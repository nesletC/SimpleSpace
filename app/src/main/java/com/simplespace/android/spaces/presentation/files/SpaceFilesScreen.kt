package com.simplespace.android.spaces.presentation.files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplespace.android.app.data.permissions.AppPermission
import com.simplespace.android.app.data.permissions.AppPermissions
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.app.lib.screen.AppScreenWithScaffoldState
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerException
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerEvent
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerViewModel
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.lib.standard.composable.remember.rememberSaveableIndexMap
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.position.Position
import com.simplespace.android.lib.standard.state.update
import com.simplespace.android.spaces.presentation.SpaceError
import com.simplespace.android.spaces.presentation.SpaceEvent
import com.simplespace.android.spaces.presentation.SpacesViewModel
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesAppBar
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesContentFrame
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesDirectoryTitleHolder
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesOverlayVisibilitySaver
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesOverlayVisibilityStates
import com.simplespace.android.spaces.presentation.files.components.SpaceFilesOverlays

@Composable
fun SpaceFilesScreen(
    preferences: AppPreferencesData,
    spacesViewModel: SpacesViewModel,
    viewModel: SpaceFilesViewModel,
    externalFileManagerViewModel: SimpleFileManagerViewModel,
    navigate: (Route) -> Unit
) {
    AppScreenWithScaffoldState(preferences = preferences) {

        SpaceFilesScreen(
            spacesViewModel = spacesViewModel,
            viewModel = viewModel,
            externalFileManagerViewModel = externalFileManagerViewModel,
            navigate = navigate,
            showSnackbar = { message ->

                it.snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
        )
    }
}

@Composable
fun AppScreenScope.SpaceFilesScreen(
    spacesViewModel: SpacesViewModel,
    viewModel: SpaceFilesViewModel,
    externalFileManagerViewModel: SimpleFileManagerViewModel,
    navigate: (Route) -> Unit,
    showSnackbar: suspend (String) -> Unit,
) {

    val spacesState = spacesViewModel.state.collectAsStateWithLifecycle()

    val filesState = viewModel.state.collectAsStateWithLifecycle()

    val fileManagerState = viewModel.fileManagerState.collectAsStateWithLifecycle()

    val isExternalFileManagerResultAvailable =
        externalFileManagerViewModel.resultAvailable.collectAsStateWithLifecycle()

    val permissionRequestManager =
        AppPermissions.rememberRequests()

    val requestStoragePermission: () -> Unit = {

        permissionRequestManager.launch(
            listOf(
                AppPermission.MANAGE_EXTERNAL_STORAGE,
                AppPermission.CAMERA,
                AppPermission.MICROPHONE,
                AppPermission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    val overlayVisibilityStates =
        rememberSaveable(saver = SpaceFilesOverlayVisibilitySaver) {
            mutableStateOf(SpaceFilesOverlayVisibilityStates())
        }

    Box(fillMaxSize) {

        val onTemporarySelection = fileManagerState.value.temporarySelection != null

        val isNoSelectionActive =
            fileManagerState.value.selectionDescription == null && !onTemporarySelection

        val sideBarVisible =
            rememberSaveable {
                mutableStateOf(
                    when (screenMode) {
                        Position.ScreenMode.PORTRAIT -> false
                        Position.ScreenMode.LANDSCAPE -> true
                    }
                )
            }

        val titleHolder: @Composable () -> Unit = {

            SpaceFilesDirectoryTitleHolder(
                title = if (fileManagerState.value.isNothing)
                    "SimpleSpaces"
                else
                    fileManagerState.value.name,
                selectionAction = filesState.value.selectionAction,
                spacesState = spacesState.value,
                fileManagerState = fileManagerState.value,
                onEvent = spacesViewModel::onEvent,
                onFileManagerEvent = viewModel::onFileManagerEvent
            )
        }

        val appBarHeight = fontSize.title.lineHeight * 2

        val spacesSideBarDropDownVisibilities =

            rememberSaveableIndexMap<Boolean>()

        LaunchedEffect(key1 = spacesState.value.overview) {

            spacesSideBarDropDownVisibilities.clear()

            spacesState.value.overview.forEach {

                if (
                    (it.value.appDirectoryExists || it.value.customDirectories.isNotEmpty()) &&
                    spacesSideBarDropDownVisibilities[it.key] == null
                )
                    spacesSideBarDropDownVisibilities[it.key] = true
            }
        }

        Column(fillMaxSize) {

            if (screenMode == Position.ScreenMode.LANDSCAPE)
                SpacerH(height = appBarHeight)
            else
                titleHolder()

            Box(modifier = Modifier.weight(1f)) {

                SpaceFilesContentFrame(
                    sideBarVisible = sideBarVisible,
                    spacesUIState = spacesState.value,
                    spacesDropDownVisibilityStates = spacesSideBarDropDownVisibilities,
                    onSpaceEvent = spacesViewModel::onEvent,
                    fileManagerState = fileManagerState.value,
                    onFileManagerEvent = viewModel::onFileManagerEvent,
                    navigate = navigate,
                    requestStoragePermission = requestStoragePermission,
                )
            }

            if (screenMode == Position.ScreenMode.PORTRAIT)
                SpacerH(height = appBarHeight)
        }

        Box(
            modifier = Modifier.align(
                if (screenMode == Position.ScreenMode.LANDSCAPE)
                    Alignment.TopCenter
                else
                    Alignment.BottomCenter
            )
        ) {

            SpaceFilesAppBar(
                height = appBarHeight,
                isNothing = fileManagerState.value.isNothing,
                onSelection = fileManagerState.value.selectionDescription != null,
                onTemporarySelection = onTemporarySelection,
                allSelected = fileManagerState.value.temporarySelection?.allSelected ?: false,
                isNoSelectionActive = isNoSelectionActive,
                toggleSideBar = { sideBarVisible.update { !it } },
                saveSelection = {
                    overlayVisibilityStates.update {
                        it.copy(
                            selectionPurpose = true
                        )
                    }
                },
                onImportRequested = {

                    overlayVisibilityStates.update {
                        it.copy(
                            launchingImport = true
                        )
                    }
                },
                onFileManagerEvent = viewModel::onFileManagerEvent,
                directoryTitleHolder = titleHolder
            )
        }
    }

    SpaceFilesOverlays(
        visibilities = overlayVisibilityStates,
        files = filesState.value,
        selectionDescription = fileManagerState.value.selectionDescription,
        onEvent = viewModel::onEvent,
        onFileManagerEvent = viewModel::onFileManagerEvent,
        onExternalFileManagerEvent = externalFileManagerViewModel::onEvent,
        requestStoragePermission = requestStoragePermission
    )

    permissionRequestManager.Dialogs(scope = this)

    LaunchedEffect(Unit) {

        spacesViewModel.onError.collect {

            showSnackbar(
                when (it) {
                    SpaceError.IO_EXCEPTION -> "An I/O error occurred"
                    SpaceError.SPACE_TIMED_OUT -> "This space was timed out"
                }
            )
        }
    }

    LaunchedEffect(Unit) {

        spacesViewModel.openSpaceEdit.collect {

            navigate(Route.SpaceEdit)
        }
    }

    LaunchedEffect(Unit) {

        spacesViewModel.openAuthentication.collect {

            navigate(Route.SpaceAuthentication)
        }
    }

    LaunchedEffect(Unit) {

        externalFileManagerViewModel.openFileManager.collect {
            navigate(Route.SpaceFileManagerExternal)
        }
    }

    LaunchedEffect(Unit) {

        spacesViewModel.setRootDirectory.collect {

            viewModel.onFileManagerEvent(it)
        }
    }

    LaunchedEffect(Unit) {

        spacesViewModel.setNothing.collect {

            viewModel.onFileManagerEvent(AESFileManagerEvent.OpenNothing)
        }
    }

    LaunchedEffect(Unit) {

        viewModel.exception.collect { exception ->

            showSnackbar(

                when (exception) {

                    is AESFileManagerException.EncryptedFile -> {
                        when (exception) {
                            AESFileManagerException.EncryptedFile.FileName -> "Conflicting file names"
                            AESFileManagerException.EncryptedFile.Key -> "Invalid key"
                            AESFileManagerException.EncryptedFile.Name -> "Conflicting name"
                        }
                    }

                    is AESFileManagerException.File -> {
                        when (exception) {
                            AESFileManagerException.File.Rename -> "Conflicting name"
                        }
                    }
                }
            )
        }
    }

    LaunchedEffect(Unit) {

        viewModel.activeKeyUsed.collect {

            spacesViewModel.onEvent(SpaceEvent.UserActivity)
        }
    }

    collectExternalFileManagerResult(
        isRequested = filesState.value.importRequested,
        isReady = isExternalFileManagerResultAvailable.value
    ) {

        viewModel.onEvent(SpaceFilesEvent.Import.OnResult)
        externalFileManagerViewModel.onEvent(SimpleFileManagerEvent.Collected)
    }

    collectExternalFileManagerResult(
        isRequested = filesState.value.exportRequested,
        isReady = isExternalFileManagerResultAvailable.value
    ) {

        viewModel.onEvent(SpaceFilesEvent.Export.OnResult)
        externalFileManagerViewModel.onEvent(SimpleFileManagerEvent.Collected)
    }
}

@Composable
private fun collectExternalFileManagerResult(
    isRequested: Boolean,
    isReady: Boolean,
    onResult: () -> Unit,
) {
    if (isRequested && isReady){
        onResult()
    }
}