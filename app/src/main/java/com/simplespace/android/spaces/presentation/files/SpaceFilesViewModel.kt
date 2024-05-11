package com.simplespace.android.spaces.presentation.files

import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionAction
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerViewModel
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpaceFilesViewModel: AESFileManagerViewModel() {

    private val _state = MutableStateFlow(SpaceFilesState())

    val state = _state.asStateFlow()

    private var importSource: SimpleFileManagerReceiver.FileSelection? = null

    private var exportTarget: SimpleFileManagerReceiver.SelectedDirectory? = null


    fun onEvent(
        event: SpaceFilesEvent
    ) {
        when (event) {

            is SpaceFilesEvent.SelectionPurposeUpdated -> {

                if (event.purpose == AESFileManagerSelectionPurpose.DELETE) {

                    _state.update { it.copy(
                        selectionPurpose = event.purpose,
                        selectionAction = AESFileManagerSelectionAction.Delete
                    )}

                    onFileManagerEvent(AESFileManagerEvent.SavingSelection)
                }
                else
                    _state.update {
                        it.copy(
                            selectionPurpose = event.purpose
                        )
                    }
            }

            is SpaceFilesEvent.SelectionActionUpdated ->
                _state.update { it.copy(
                    selectionAction = event.action
                ) }


            is SpaceFilesEvent.Import -> {

                when (event) {

                    SpaceFilesEvent.Import.Aborted -> {
                        importSource = null
                        _state.update {
                            it.copy(
                                importRequested = false,
                                importSelectionReceived = false
                            )
                        }
                    }
                    is SpaceFilesEvent.Import.Request -> {

                        importSource = event.receiver
                        _state.update {
                            it.copy(
                                importRequested = true
                            )
                        }
                    }
                    is SpaceFilesEvent.Import.OnResult ->
                        _state.update { it.copy(
                            importSelectionReceived = true,
                            importRequested = false
                        )}

                    is SpaceFilesEvent.Import.Action -> {

                        val importSource = importSource?:return

                        _state.update {it.copy(
                            importSelectionReceived = false
                        ) }

                        onFileManagerEvent(AESFileManagerEvent.Import(
                            source = importSource.fileManagerDirectory,
                            selection = importSource.fileNames,
                            action = event.action,
                            encrypt = event.encrypt
                        ))
                    }
                }
            }

            is SpaceFilesEvent.Export -> {

                when (event) {

                    SpaceFilesEvent.Export.Aborted -> {

                        exportTarget = null

                        _state.update { it.copy(
                            exportDirectoryReceived = false,
                            exportRequested = false
                        ) }
                    }
                    is SpaceFilesEvent.Export.Request -> {

                        exportTarget = event.receiver

                        _state.update {it.copy(
                            exportRequested = true
                        ) }
                    }
                    is SpaceFilesEvent.Export.OnResult ->
                        _state.update { it.copy(
                            exportDirectoryReceived = true,
                            exportRequested = false
                        ) }

                    is SpaceFilesEvent.Export.Action -> {

                        val exportTarget = exportTarget?:return

                        _state.update { it.copy(
                            exportDirectoryReceived = false
                        ) }

                        onFileManagerEvent(
                            AESFileManagerEvent.Export(
                                target = exportTarget.fileManagerDirectory,
                                action = event.action,
                            )
                        )
                    }
                }
            }
        }
    }
}