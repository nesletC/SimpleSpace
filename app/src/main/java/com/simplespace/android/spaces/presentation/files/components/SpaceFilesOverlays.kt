package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.data.customStorages.CustomAppStorage
import com.simplespace.android.app.data.customStorages.CustomAppStorages
import com.simplespace.android.app.data.permissions.AppPermissions
import com.simplespace.android.app.data.permissions.AppPermission
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlay
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlayConfirmation
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionAction
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionDescription
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurposes
import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerEvent
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerReceiver
import com.simplespace.android.lib.standard.comparison.equalsAny
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.state.update
import com.simplespace.android.spaces.presentation.files.SpaceFilesEvent
import com.simplespace.android.spaces.presentation.files.SpaceFilesState


@Composable
fun AppScreenScope.SpaceFilesOverlays(
    visibilities: MutableState<SpaceFilesOverlayVisibilityStates>,
    files: SpaceFilesState,
    selectionDescription: AESFileManagerSelectionDescription?,
    onEvent: (SpaceFilesEvent) -> Unit,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
    onExternalFileManagerEvent: (SimpleFileManagerEvent) -> Unit,
    requestStoragePermission: () -> Unit,
) {

    val selectionActionConfirmation = rememberSaveable {
        mutableStateOf(false)
    }

    val selectionPurposeLabel = files.selectionPurpose?.let {
        AESFileManagerSelectionPurposes.labels[it]
    }
        ?:""

    SelectionPurposeOverlay(
        visible = visibilities.value.selectionPurpose,
        updateVisibilities = visibilities::update,
        onEvent = onEvent
    )

    SelectionActionOverlay(
        selectionAction = visibilities.value.selectionAction,
        importSelectionReceived = files.importSelectionReceived,
        exportDirectoryReceived = files.exportDirectoryReceived,
        selectionPurpose = files.selectionPurpose,
        selectionPurposeLabel = selectionPurposeLabel,
        updateVisibilities = visibilities::update,
        onEvent = onEvent,
        onFileManagerEvent = onFileManagerEvent
    )


    SelectionActionConfirmation(
        visible = selectionActionConfirmation.value,
        escape = { selectionActionConfirmation.value = false },
        selectionAction = files.selectionAction,
        selectionPurposeLabel = selectionPurposeLabel,
        localSelectionDescriptionString = selectionDescription?.let {

            "${it.filesCount} files and ${it.directoriesCount} directories"
        }
            ?:"",
        onEvent = onEvent,
        onFileManagerEvent = onFileManagerEvent
    )

    ExternalFileManagerLauncherOverlay(
        launchingImport = visibilities.value.launchingImport,
        launchingExport = visibilities.value.launchingExport,
        escape = {
            if (visibilities.value.launchingImport)
                visibilities.update {
                    it.copy(
                        launchingImport = false
                    )
                }
            else
                visibilities.update {
                    it.copy(
                        launchingExport = false
                    )
                }
        },
        onEvent = onEvent,
        onExternalFileManagerEvent = onExternalFileManagerEvent,
        requestStoragePermission = requestStoragePermission
    )
}

@Composable
private fun AppScreenScope.SelectionPurposeOverlay(
    visible: Boolean,
    updateVisibilities:
        ((SpaceFilesOverlayVisibilityStates) -> SpaceFilesOverlayVisibilityStates) -> Unit,
    onEvent: (SpaceFilesEvent.SelectionPurposeUpdated) -> Unit,
) {

    SimpleOverlay(
        visible = visible,
        escape = {
            updateVisibilities {
                it.copy(
                    selectionPurpose = false,
                )
            }
        },
    ) {

        AESFileManagerSelectionPurpose.entries.forEach{

            Row(
                modifier = fillMaxWidth
                    .padding(8.dp)
                    .clickable {
                        onEvent(SpaceFilesEvent.SelectionPurposeUpdated(it))
                        updateVisibilities {
                            it.copy(
                                selectionPurpose = false,
                                selectionAction = true
                            )
                        }
                    }
            ) {

                texts.h3(AESFileManagerSelectionPurposes.labels[it]!!)
            }
        }

        Row(
            modifier = fillMaxWidth
                .padding(8.dp)
                .clickable {
                    updateVisibilities {
                        it.copy(
                            launchingExport = true
                        )
                    }
                }
        ) {

            texts.h3("Export")
        }
    }
}

@Composable
private fun AppScreenScope.SelectionActionOverlay(
    selectionAction: Boolean,
    importSelectionReceived: Boolean,
    exportDirectoryReceived: Boolean,
    selectionPurpose: AESFileManagerSelectionPurpose?,
    selectionPurposeLabel: String,
    updateVisibilities:
        ((SpaceFilesOverlayVisibilityStates) -> SpaceFilesOverlayVisibilityStates) -> Unit,
    onEvent: (SpaceFilesEvent) -> Unit,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
) {

    SimpleOverlay(
        visible =
            selectionAction ||
                importSelectionReceived ||
                exportDirectoryReceived,
        escape = {
            if (selectionAction) {

                updateVisibilities{ it.copy(
                    selectionAction = false,
                )}
                onEvent(SpaceFilesEvent.SelectionPurposeUpdated(null))
            }
            else if (importSelectionReceived)
                onEvent(SpaceFilesEvent.Import.Aborted)
            else if (exportDirectoryReceived)
                onEvent(SpaceFilesEvent.Export.Aborted)
        },
    ){

        val optionCopy = rememberSaveable { mutableStateOf(false) }
        val optionOverwrite = rememberSaveable { mutableStateOf(false) }

        @Composable
        fun Option(label: String, state: MutableState<Boolean>) {

            Row(
                Modifier
                    .padding(5.dp)
                    .clickable {
                        state.value = !state.value
                    }
            ){

                texts.font(label)

                SpacerW(width = 5.dp)

                mediumIcons.CheckBox(isChecked = state.value)
            }
        }

        fun saveSelection() {

            selectionPurpose?.let {

                onFileManagerEvent(
                    AESFileManagerEvent.SavingSelection
                )

                updateVisibilities{ it.copy(
                    selectionAction = false
                )}
            }
        }

        AnimatedVisibility(
            visible =
                selectionPurpose == AESFileManagerSelectionPurpose.ENCRYPT ||
                    selectionPurpose == AESFileManagerSelectionPurpose.DECRYPT ||
                    importSelectionReceived ||
                    exportDirectoryReceived
        ) {

            Option(
                label = "keep original",
                state = optionCopy
            )

            SpacerH(height = 10.dp)
        }

        AnimatedVisibility(
            visible =
            !selectionPurpose.equalsAny(
                AESFileManagerSelectionPurpose.DELETE, null
            ) ||
                    importSelectionReceived ||
                    exportDirectoryReceived
        ) {

            Option(
                label = "Overwrite",
                state = optionOverwrite
            )

            SpacerH(height = 10.dp)
        }

        AnimatedVisibility(
            visible = !selectionPurpose.equalsAny(null, AESFileManagerSelectionPurpose.DELETE)
        ) {

            mediumButtons.IconTextButton(
                icon = Icons.Default.PlayArrow,
                contentDescription = "Select directory",
                text = "Select directory",
                onClick = ::saveSelection
            )

            SpacerH(height = 10.dp)

            mediumButtons.IconTextButton(
                icon = Icons.Default.PlayArrow,
                contentDescription = selectionPurposeLabel,
                text = selectionPurposeLabel,
            ) {
                saveSelection()
                updateVisibilities { it.copy(
                    selectionAction = true
                )}
            }

            SpacerH(height = 10.dp)
        }

        AnimatedVisibility(visible = importSelectionReceived) {

            Row{

                mediumButtons.IconTextButton(
                    icon = Icons.Default.Upload,
                    contentDescription = "Import",
                    text = "Import",
                ) {
                    onEvent(
                        SpaceFilesEvent.Import.Action(
                            action = AESFileManagerSelectionAction.Move(
                                copy = optionCopy.value,
                                overwrite = optionOverwrite.value
                            ),
                            encrypt = false
                        )
                    )
                }

                SpacerH(height = 10.dp)

                mediumButtons.IconTextButton(
                    icon = Icons.Default.Lock,
                    contentDescription = "Encrypt",
                    text = "Encrypt",
                ) {
                    onEvent(
                        SpaceFilesEvent.Import.Action(
                            action = AESFileManagerSelectionAction.Move(
                                copy = optionCopy.value,
                                overwrite = optionOverwrite.value
                            ),
                            encrypt = true
                        )
                    )
                }

                SpacerH(height = 10.dp)
            }
        }

        AnimatedVisibility(visible = exportDirectoryReceived) {

            mediumButtons.IconTextButton(
                icon = Icons.Default.Download,
                contentDescription = "Export",
                text = "Export",
            ) {
                onEvent(
                    SpaceFilesEvent.Export.Action(
                        action = AESFileManagerSelectionAction.Move(
                            copy = optionCopy.value,
                            overwrite = optionOverwrite.value
                        ),
                    )
                )
            }

            SpacerH(height = 10.dp)
        }
    }
}

@Composable
private fun AppScreenScope.SelectionActionConfirmation(
    visible: Boolean,
    escape: () -> Unit,
    selectionAction: AESFileManagerSelectionAction?,
    selectionPurposeLabel: String,
    localSelectionDescriptionString: String,
    onEvent: (SpaceFilesEvent) -> Unit,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
) {

    SimpleOverlayConfirmation(

        visible = visible,
        label = "Confirm",
        message =
            selectionPurposeLabel+
                " " +
                localSelectionDescriptionString,
        escape = escape,
        onAbort = {

            onFileManagerEvent(
                AESFileManagerEvent.CancelingSelection
            )
        }
    ) {

        selectionAction?.let {

            onFileManagerEvent(AESFileManagerEvent.ExecutingSelectionAction(it))
        }

        onEvent(SpaceFilesEvent.SelectionActionUpdated(null))
    }
}

@Composable
private fun AppScreenScope.ExternalFileManagerLauncherOverlay(
    launchingImport: Boolean,
    launchingExport: Boolean,
    escape: () -> Unit,
    onEvent: (SpaceFilesEvent) -> Unit,
    onExternalFileManagerEvent: (SimpleFileManagerEvent.Launch) -> Unit,
    requestStoragePermission: () -> Unit,
) {

    SimpleOverlay(visible = launchingImport || launchingExport, escape = escape) {

        CustomAppStorage.entries.forEach{ entry ->

            Row(fillMaxWidth
                .padding(10.dp)
                .clickable{

                    if (AppPermissions.isGrantedCurrently(AppPermission.MANAGE_EXTERNAL_STORAGE)) {

                        val rootDirectory = simpleMutableFileOf(
                            CustomAppStorages.directoryIndex(entry)!!
                        )

                        if (launchingImport){

                            val receiver =
                                SimpleFileManagerReceiver.FileSelection(
                                    rootDirectory = rootDirectory,
                                )

                            onEvent(
                                SpaceFilesEvent.Import.Request(
                                    receiver = receiver
                                )
                            )

                            onExternalFileManagerEvent(
                                SimpleFileManagerEvent.Launch(
                                    receiver = receiver
                                )
                            )
                        }

                        else if (launchingExport){

                            val receiver = SimpleFileManagerReceiver.SelectedDirectory(
                                rootDirectory = rootDirectory
                            )

                            onEvent(
                                SpaceFilesEvent.Export.Request(
                                    receiver = receiver
                                )
                            )

                            onExternalFileManagerEvent(
                                SimpleFileManagerEvent.Launch(
                                    receiver = receiver
                                )
                            )
                        }
                    }
                    else
                        requestStoragePermission()
            }) {

                SpacerH(height = 5.dp)

                texts.h3(text = CustomAppStorages.label(entry)!!)

                SpacerH(height = 5.dp)
            }
        }
    }
}