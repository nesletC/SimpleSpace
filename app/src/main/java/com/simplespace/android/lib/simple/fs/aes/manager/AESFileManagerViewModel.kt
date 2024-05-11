package com.simplespace.android.lib.simple.fs.aes.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.lib.simple.fs.aes.manager.util.aesFileManagerSelectionDescription
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class AESFileManagerViewModel : ViewModel() {

    private val _exception =
        MutableSharedFlow<AESFileManagerException>()

    val exception = _exception.asSharedFlow()

    private val _activeKeyUsed =
        MutableSharedFlow<Unit>()

    val activeKeyUsed = _activeKeyUsed.asSharedFlow()

    private val _fileManagerState =
        MutableStateFlow(AESFileManagerState())

    val fileManagerState = _fileManagerState.asStateFlow()

    fun onFileManagerEvent(event: AESFileManagerEvent) {
        viewModelScope.launch {

            val result = manager(event)

            if (result.isNothingOpenedUpdated) {
                _fileManagerState.update { it.copy(
                    isNothing = manager.isNothingState
                ) }
            }

            if (result.isInRootDirectoryUpdated)
                _fileManagerState.update { it.copy(
                    isRootDirectory = manager.isInRootDirectoryState
                ) }

            if (result.directoryUpdated) {

                _fileManagerState.update { it.copy(
                    content = manager.aesContent,
                    name = manager.meta.realName
                ) }
            }

            if (result.activeKeyUsed)
                _activeKeyUsed.emit(Unit)

            if (result.detailsUpdated)
                _fileManagerState.update { it.copy(
                    details = manager.detailsState
                ) }

            if (result.selectionUpdated) {

                _fileManagerState.update { it.copy(
                    selectionDescription = manager.selectionState?.selection?.let{ selection ->
                        aesFileManagerSelectionDescription(selection)
                    }
                ) }
            }

            if (result.temporarySelectionUpdated) {
                _fileManagerState.update { it.copy(
                    temporarySelection = manager.temporarySelectionState
                )}
            }

            result.exception?.let {
                _exception.emit(it)
            }
        }
    }

    private val manager = AESFileManager()
}