package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionAction
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerState
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.spaces.presentation.SpaceEvent
import com.simplespace.android.spaces.presentation.SpacesUIState


@Composable
fun AppScreenScope.SpaceFilesDirectoryTitleHolder(
    title: String,
    selectionAction: AESFileManagerSelectionAction?,
    spacesState: SpacesUIState,
    fileManagerState: AESFileManagerState,
    onEvent: (SpaceEvent) -> Unit,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
) {

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){

        SpacerW(width = 15.dp)

        RootDirectoryOptions(
            visible = fileManagerState.isRootDirectory && !fileManagerState.isNothing,
            edit = {
                spacesState.current?.let{
                    onEvent(SpaceEvent.LaunchingSpaceEdit.Update(it))
                }
            },
            logOut = {
                spacesState.current?.let {

                    onFileManagerEvent(AESFileManagerEvent.OpenNothing)
                    onEvent(SpaceEvent.StoppingSession(it))
                }
            }
        )

        SelectionTargetConfirmationOption(
            visible =
                !fileManagerState.isNothing &&
                fileManagerState.temporarySelection == null &&
                fileManagerState.selectionDescription != null
        ) {
            selectionAction?.let{
                onFileManagerEvent(
                    AESFileManagerEvent.ExecutingSelectionAction(
                        it
                    )
                )
            }
        }

        OpenParentOption(visible = !fileManagerState.isRootDirectory && !fileManagerState.isNothing) {
            onFileManagerEvent(AESFileManagerEvent.OpenParent)
        }

        texts.h1(title)
    }
}

@Composable
private fun AppScreenScope.RootDirectoryOptions(
    visible: Boolean,
    edit: () -> Unit,
    logOut: () -> Unit,
){

    AnimatedVisibility(
        visible = visible
    ) {

        Row{

            mediumButtons.IconButton(
                icon = Icons.Default.Edit,
                contentDescription = "Edit the space",
                onClick = edit
            )

            SpacerW(15.dp)

            mediumButtons.IconButton(
                icon = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Log out",
                onClick = logOut
            )

            SpacerW(15.dp)
        }
    }
}

@Composable
private fun AppScreenScope.SelectionTargetConfirmationOption(
    visible: Boolean,
    onClick: () -> Unit,
) {

    AnimatedVisibility(visible = visible) {

        Row {

            mediumButtons.IconButton(
                icon = Icons.Default.Check,
                contentDescription = "Perform selection action",
                onClick = onClick
            )

            SpacerW(15.dp)
        }
    }
}

@Composable
private fun AppScreenScope.OpenParentOption(
    visible: Boolean,
    onClick: () -> Unit,
) {

    AnimatedVisibility(
        visible = visible
    ) {

        Row {

            mediumButtons.IconButton(
                icon = Icons.Default.ArrowUpward,
                contentDescription = "Open parent",
                onClick = onClick
            )

            SpacerW(15.dp)
        }
    }
}