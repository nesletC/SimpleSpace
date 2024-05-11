package com.simplespace.android.spaces.presentation.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Storage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplespace.android.app.data.customStorages.CustomAppStorage
import com.simplespace.android.app.data.customStorages.CustomAppStorages
import com.simplespace.android.app.data.permissions.AppPermission
import com.simplespace.android.app.data.permissions.AppPermissions
import com.simplespace.android.lib.simple.compose.input.SimpleInput
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlay
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerEvent
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerReceiver
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerViewModel
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.standard.color.alpha
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.string.nullIfBlank
import com.simplespace.android.spaces.model.SpaceDirectoryAccess
import com.simplespace.android.spaces.model.SpaceMeta
import com.simplespace.android.spaces.presentation.edit.SpaceDirectoryUpdateMeta
import com.simplespace.android.spaces.presentation.edit.SpaceEditEvent
import com.simplespace.android.spaces.presentation.edit.SpaceEditViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppScreenScope.SpaceEditScreenContent(
    sourceMeta: SpaceMeta,
    viewModel: SpaceEditViewModel,
    externalFileManagerViewModel: SimpleFileManagerViewModel,
) {

    val permissionRequestManager =
        AppPermissions.rememberRequests()

    val externalFileManagerLaunchOverlayVisible = rememberSaveable{
        mutableStateOf(false)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .width(
                    maxWidthCalculations
                        .dp(1200)
                        .maxPercentage(95)
                        .dp
                )
                .verticalScroll(rememberScrollState()),
        ) {

            val focusManager = LocalFocusManager.current

            fun moveOn() = focusManager.moveFocus(FocusDirection.Down)

            //-------  Real mame ------------

            SpacerH(height = 10.dp)

            val realName = rememberSaveable {
                mutableStateOf(sourceMeta.realName)
            }

            SimpleInput(
                value = realName.value,
                onValueChange = {

                    realName.value = it
                },
                onSubmit = ::moveOn
            )

            SpacerH(height = 10.dp)


            //-------  Max timeout ------------

            val maxTimeout = rememberSaveable {
                mutableIntStateOf(sourceMeta.maxTimeoutInMinutes)
            }

            SimpleInput(
                value = "${maxTimeout.intValue}",
                allowNumbersOnly = true,
                onValueChange = {
                    maxTimeout.intValue = it.nullIfBlank()?.toInt()?:sourceMeta.maxTimeoutInMinutes
                },
                onSubmit = ::moveOn,
            )

            SpacerH(height = 10.dp)


            //-------  Protected directory ------------

            val defaultProtectedDirectoryFileName = sourceMeta.protectedDirectory.fileName

            val protectedDirectoryLabel = "protected"

            val defaultProtectedDirectoryKey = sourceMeta.protectedDirectory.key

            val protectedDirectoryUpdateData = SpaceDirectoryUpdateMeta(
                cancel = mutableStateOf(false),
                defaultFileName = defaultProtectedDirectoryFileName,
                fileName = rememberSaveable{
                    mutableStateOf(defaultProtectedDirectoryFileName)
                },
                defaultKey = defaultProtectedDirectoryKey,
                key = rememberSaveable{
                    mutableStateOf(defaultProtectedDirectoryKey)
                },
                defaultLabel = protectedDirectoryLabel,
                label = rememberSaveable{
                    mutableStateOf(protectedDirectoryLabel)
                },
            )

            SpaceDirectoryEdit(
                moveOn = ::moveOn,
                updateData = protectedDirectoryUpdateData,
                isLabelEditable = false,
            )

            SpacerH(height = 10.dp)

            fun protectedDirectoryAccess() : SpaceDirectoryAccess =
                SpaceDirectoryAccess(
                    fileName = protectedDirectoryUpdateData.fileName.value,
                    key = protectedDirectoryUpdateData.key.value
                )


            //-------  Open App directory ------------


            val defaultAppDirectoryFileName =
                sourceMeta.appDirectory?.fileName?: AESFileSystem.randomDirectoryName()

            val defaultAppDirectoryKey =
                sourceMeta.appDirectory?.key?: SimpleEncryption.randomKey()

            val appDirectoryLabel = "app directory"

            val appDirectoryUpdateData = SpaceDirectoryUpdateMeta(
                cancel = rememberSaveable{
                    mutableStateOf(sourceMeta.appDirectory == null)
                },
                defaultFileName = defaultAppDirectoryFileName,
                fileName = rememberSaveable{
                    mutableStateOf(defaultAppDirectoryFileName)
                },
                defaultKey = defaultAppDirectoryKey,
                key = rememberSaveable{
                    mutableStateOf(defaultAppDirectoryKey)
                },
                defaultLabel = appDirectoryLabel,
                label = rememberSaveable{
                    mutableStateOf(appDirectoryLabel)
                },
            )

            SpaceDirectoryEditCancellable(
                moveOn = ::moveOn,
                updateData = appDirectoryUpdateData,
                isLabelEditable = false,
            )

            SpacerH(height = 10.dp)

            fun appDirectoryAccess() : SpaceDirectoryAccess? {
                val fileName = appDirectoryUpdateData.fileName.value
                val key = appDirectoryUpdateData.key.value

                return if (appDirectoryUpdateData.cancel.value)
                    null
                else
                    SpaceDirectoryAccess(
                        fileName = fileName,
                        key = key
                    )
            }


            //-------  External Directories -----------

            SpaceDirectoriesEdit(state = state.value, moveOn = ::moveOn)

            val labelAddExternalDirectory = "Add from..."

            mediumButtons.IconTextButton(
                icon = Icons.Default.Add,
                contentDescription = labelAddExternalDirectory,
                text = labelAddExternalDirectory,
            ) {

                if (AppPermissions.isGrantedCurrently(AppPermission.MANAGE_EXTERNAL_STORAGE))
                    externalFileManagerLaunchOverlayVisible.value = true
                else
                    permissionRequestManager.launch(listOf(AppPermission.MANAGE_EXTERNAL_STORAGE))
            }

            SpacerH(10.dp)

            Divider(modifier = fillMaxWidth
                .height(1.dp)
                .background(theme.surfaceText.alpha(128)))

            //-------  Save  ------------

            SpacerH(10.dp)

            mediumButtons.TextButton(
                text = "Save",
            ) {

                viewModel.onEvent(
                    SpaceEditEvent.Save(
                        spaceName = realName.value,
                        maxTimeoutInMinutes = maxTimeout.value,
                        protectedDirectory = protectedDirectoryAccess(),
                        appDirectory = appDirectoryAccess(),
                    )
                )
            }
        }
    }

    SimpleOverlay(
        visible = externalFileManagerLaunchOverlayVisible.value,
        escape = { externalFileManagerLaunchOverlayVisible.value = false }
    ) {

        SpacerH(height = 10.dp)

        CustomAppStorage.entries.forEach{

            Row(
                fillMaxWidth
                    .clickable {

                        externalFileManagerLaunchOverlayVisible.value = false

                        if (AppPermissions.isGrantedCurrently(AppPermission.MANAGE_EXTERNAL_STORAGE)) {

                            val receiver =
                                SimpleFileManagerReceiver.SelectedDirectory(
                                    rootDirectory = simpleMutableFileOf(
                                        CustomAppStorages.directoryIndex(it)!!
                                    )
                                )

                            viewModel.onEvent(
                                SpaceEditEvent.NewExternalSpaceDirectory.Request(receiver = receiver)
                            )

                            externalFileManagerViewModel.onEvent(
                                SimpleFileManagerEvent.Launch(receiver = receiver)
                            )
                        }
                        else
                            permissionRequestManager.launch(listOf(AppPermission.MANAGE_EXTERNAL_STORAGE))
                    }
                    .padding(10.dp)
            ) {
                mediumIcons.Icon(icon = Icons.Default.Storage)

                SpacerW(width = 10.dp)

                texts.h3(text = CustomAppStorages.label(it)!!)
            }
        }

        SpacerH(height = 10.dp)
    }

    permissionRequestManager.Dialogs(this)

    val isExternalFileManagerResultAvailable =
        externalFileManagerViewModel.resultAvailable.collectAsStateWithLifecycle()

    collectExternalFileManagerResult(
        isRequested = state.value.directoryRequested,
        isReady = isExternalFileManagerResultAvailable.value
    ) {

        viewModel.onEvent(SpaceEditEvent.NewExternalSpaceDirectory.OnResult)
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