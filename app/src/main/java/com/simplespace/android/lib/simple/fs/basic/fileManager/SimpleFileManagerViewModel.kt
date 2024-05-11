package com.simplespace.android.lib.simple.fs.basic.fileManager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimpleFileManagerViewModel: ViewModel() {

    private val _openFileManager = MutableSharedFlow<Unit>()

    private val _closeFileManager = MutableSharedFlow<Unit>()


    private val _resultAvailable = MutableStateFlow(false)

    private val _directoryContent =
        MutableStateFlow<FileNamesWithDirectoryPartition?>(null)


    val openFileManager = _openFileManager.asSharedFlow()

    val closeFileManager = _closeFileManager.asSharedFlow()


    val resultAvailable = _resultAvailable.asStateFlow()

    val directoryContent = _directoryContent.asStateFlow()

    var receiver: SimpleFileManagerReceiver? = null
    private set

    var fileManagerDirectoryMinLevel = -1

    fun onEvent(event: SimpleFileManagerEvent) {

        viewModelScope.launch {
            when (event) {

                is SimpleFileManagerEvent.Launch -> {

                    receiver = event.receiver

                    fileManagerDirectoryMinLevel = event.receiver.fileManagerDirectory.getLevel()

                    _directoryContent.update {
                        receiver!!.fileManagerDirectory.content
                    }

                    _openFileManager.emit(Unit)
                }

                is SimpleFileManagerEvent.DirectoryUpdated -> _directoryContent.update {
                    receiver?.fileManagerDirectory?.content
                }

                is SimpleFileManagerEvent.OnResult -> {

                    val receiver = receiver!!
                    val directoryContent = _directoryContent.value!!
                    if (
                        receiver is SimpleFileManagerReceiver.FileSelection &&
                        event.result is SimpleFileManagerResult.Files
                    ) {

                        event.result.directorySelection.forEach{
                            receiver.fileNames.directories.add(
                                directoryContent.directories[it]
                            )
                        }

                        event.result.fileSelection.forEach{
                            receiver.fileNames.files.add(
                                directoryContent.files[it]
                            )
                        }
                    }

                    _closeFileManager.emit(Unit)

                    _resultAvailable.update { true }
                }

                SimpleFileManagerEvent.Collected -> {

                    _directoryContent.update { null }

                    _resultAvailable.update { false }
                }
            }
        }
    }
}