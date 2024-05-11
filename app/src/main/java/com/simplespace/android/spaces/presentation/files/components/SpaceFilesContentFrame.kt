package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerState
import com.simplespace.android.lib.standard.composable.container.DynamicSideBarFrame
import com.simplespace.android.spaces.presentation.SpaceEvent
import com.simplespace.android.spaces.presentation.SpacesUIState

@Composable
fun AppScreenScope.SpaceFilesContentFrame(
    sideBarVisible: MutableState<Boolean>,
    spacesUIState: SpacesUIState,
    spacesDropDownVisibilityStates: SnapshotStateMap<Int, Boolean>,
    onSpaceEvent: (SpaceEvent) -> Unit,
    fileManagerState: AESFileManagerState,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
    navigate: (Route) -> Unit,
    requestStoragePermission: () -> Unit,
) {

    DynamicSideBarFrame(
        sideBarVisible = sideBarVisible.value,
        maxWidthCalculations = maxWidthCalculations,
        sideBarWidth = maxWidthCalculations.dp(400).minPercentage(15).dp,
        contentMinWidth = 1000.dp,
        sideBarContent = {
            SpaceFilesSideBar(
                selectedSpace = spacesUIState.current,
                spaces = spacesUIState.overview,
                dropDownVisibilityStates = spacesDropDownVisibilityStates,
                hide = { sideBarVisible.value = false },
                navigate = navigate,
                onEvent = onSpaceEvent,
                requestStoragePermission = requestStoragePermission,
            )
        },
        content = {
            SpaceFilesContent(
                isNothingOpened = fileManagerState.isNothing,
                content = fileManagerState.content,
                temporarySelection = fileManagerState.temporarySelection,
                onFileManagerEvent = onFileManagerEvent,
            )
        }
    )
}