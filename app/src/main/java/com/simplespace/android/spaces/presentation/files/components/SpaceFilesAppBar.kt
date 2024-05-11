package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.position.Position

@Composable
fun AppScreenScope.SpaceFilesAppBar(
    height: Dp,
    isNothing: Boolean,
    onSelection: Boolean,
    onTemporarySelection: Boolean,
    allSelected: Boolean,
    isNoSelectionActive: Boolean,
    toggleSideBar: () -> Unit,
    saveSelection: () -> Unit,
    onImportRequested: () -> Unit,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
    directoryTitleHolder: @Composable () -> Unit
) {
    Row(
        modifier = fillMaxWidth
            .padding(20.dp)
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            mediumButtons.IconButton(
                icon = Icons.AutoMirrored.Filled.List,
                contentDescription = "Toggle side bar",
                onClick = toggleSideBar
            )

            if (screenMode == Position.ScreenMode.LANDSCAPE) {

                SpacerW(width = 10.dp)

                directoryTitleHolder()
            }
        }

        Row(
            horizontalArrangement = Arrangement.End
        ) {

            AnimatedVisibility(
                isNoSelectionActive && !isNothing
            ) {

                Row {

                    mediumButtons.IconButton(
                        icon = Icons.Default.Upload,
                        contentDescription = "Import",
                        onClick = onImportRequested
                    )

                    SpacerW(15.dp)
                }
            }

            AnimatedVisibility(visible = !isNothing && (onTemporarySelection || isNoSelectionActive)) {

                Row {

                    mediumButtons.Button(onClick = {
                        if (isNoSelectionActive)
                            onFileManagerEvent(
                                AESFileManagerEvent.TemporarySelection.Init
                            )
                        else if (allSelected)
                            onFileManagerEvent(
                                AESFileManagerEvent.TemporarySelection.None
                            )
                        else
                            onFileManagerEvent(
                                AESFileManagerEvent.TemporarySelection.All
                            )
                    }) {
                        mediumIcons.CheckBox(isChecked = allSelected)
                    }

                    SpacerW(15.dp)
                }
            }

            AnimatedVisibility(
                visible = onSelection
            ) {

                Row {

                    mediumButtons.IconButton(
                        icon = Icons.Default.Close,
                        contentDescription = "Abort selecting"
                    ) {
                        onFileManagerEvent(
                            AESFileManagerEvent.TemporarySelection.Abort
                        )
                    }

                    SpacerW(width = 15.dp)
                }
            }

            AnimatedVisibility(
                visible = !onSelection && onTemporarySelection
            ) {

                Row {

                    mediumButtons.IconButton(
                        icon = Icons.Default.Check,
                        contentDescription = "Set the action for the Selection",
                        onClick = saveSelection
                    )

                    SpacerW(width = 15.dp)
                }
            }
        }
    }
}