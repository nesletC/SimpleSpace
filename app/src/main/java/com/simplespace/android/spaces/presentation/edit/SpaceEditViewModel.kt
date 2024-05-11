package com.simplespace.android.spaces.presentation.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.standard.iterable.list.list
import com.simplespace.android.spaces.data.SpaceAuthentication
import com.simplespace.android.spaces.data.SpaceSessions
import com.simplespace.android.spaces.data.Spaces
import com.simplespace.android.spaces.model.SpaceDirectory
import com.simplespace.android.spaces.model.SpaceDirectoryAccess
import com.simplespace.android.spaces.model.SpaceMeta
import com.simplespace.android.spaces.presentation.SpaceEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpaceEditViewModel(
    val purpose: SpaceEvent.LaunchingSpaceEdit
) : ViewModel() {

    private val spaceDirectoriesList =
        mutableListOf<SpaceDirectoryUpdateMeta>()

    private val spaceFilesList = mutableListOf<SimpleFile>()

    val meta = if (purpose is SpaceEvent.LaunchingSpaceEdit.Update)
        SpaceSessions.getMeta(purpose.sessionIndex)
    else
        SpaceMeta()

    init {

        if (purpose is SpaceEvent.LaunchingSpaceEdit.Update)
            meta?.directories?.forEach{

                spaceFilesList.add(it.file())

                spaceDirectoriesList.add(it.getUpdateMeta())
            }
    }

    private val _state = MutableStateFlow(SpaceEditState(
        directories = spaceDirectoriesList.toList(),
    ))

    val state = _state.asStateFlow()

    private val _authenticationAction =
        MutableSharedFlow<SpaceAuthentication.Action.Save>()

    val authenticationAction = _authenticationAction.asSharedFlow()


    private var newSpaceDirectory: SimpleMutableFile? = null

    fun onEvent(event: SpaceEditEvent) {

        viewModelScope.launch{

            when (event) {

                is SpaceEditEvent.Save -> {

                    val meta = SpaceMeta(
                        realName = event.spaceName,
                        maxTimeoutInMinutes = event.maxTimeoutInMinutes,
                        protectedDirectory = event.protectedDirectory,
                        appDirectory = event.appDirectory,
                        directories = list{

                            spaceDirectoriesList.forEachIndexed{ index, directory ->
                                if (!directory.cancel.value)
                                    add(
                                        SpaceDirectory(
                                            rootDirectoryIndex = spaceFilesList[index].rootDirectoryIndex,
                                            parentList = spaceFilesList[index].parentList,
                                            label = directory.label.value,
                                            access = SpaceDirectoryAccess(
                                                fileName = directory.fileName.value,
                                                key = directory.key.value
                                            )
                                        )
                                    )
                            }
                        }
                    )

                    _authenticationAction.emit(
                        when (purpose) {

                            SpaceEvent.LaunchingSpaceEdit.New -> {

                                SpaceAuthentication.Action.Save.New(meta)
                            }

                            is SpaceEvent.LaunchingSpaceEdit.Update ->

                                SpaceAuthentication.Action.Save.Update(
                                    index = purpose.sessionIndex,
                                    updatedMeta = meta,
                                )
                        }
                    )
                }

                is SpaceEditEvent.NewExternalSpaceDirectory -> {

                    when (event) {
                        is SpaceEditEvent.NewExternalSpaceDirectory.Request -> {

                            newSpaceDirectory = event.receiver.fileManagerDirectory

                            _state.update { it.copy(
                                directoryRequested = true
                            ) }

                        }

                        SpaceEditEvent.NewExternalSpaceDirectory.Aborted -> {

                            newSpaceDirectory = null

                            _state.update { it.copy(
                                directoryRequested = false
                            ) }
                        }

                        SpaceEditEvent.NewExternalSpaceDirectory.OnResult -> {

                            _state.update { it.copy(
                                directories =
                                    newSpaceDirectory?.run{

                                        child(AESFileSystem.randomDirectoryName())

                                        spaceFilesList.add(this)

                                        spaceDirectoriesList.add(
                                            randomSpaceDirectoryMeta(this)
                                        )

                                        spaceDirectoriesList.toList()

                                    }
                                        ?:it.directories,
                                directoryRequested = false
                            )}

                            newSpaceDirectory = null
                        }
                    }
                }
            }
        }
    }

    private fun randomSpaceDirectoryMeta(file: SimpleFile) : SpaceDirectoryUpdateMeta {

        val defaultKey = SimpleEncryption.randomKey()

        return SpaceDirectoryUpdateMeta(
            cancel = mutableStateOf(false),
            defaultFileName = file.name,
            fileName = mutableStateOf(file.name),
            defaultKey = defaultKey,
            key = mutableStateOf(defaultKey),
            defaultLabel = Spaces.DEFAULT_DIRECTORY_KEY,
            label = mutableStateOf(Spaces.DEFAULT_DIRECTORY_KEY),
        )
    }

    private fun SpaceDirectory.getUpdateMeta() = file().let { file ->

        SpaceDirectoryUpdateMeta(
            cancel = mutableStateOf(false),
            defaultFileName = file.name,
            fileName = mutableStateOf(file.name),
            defaultKey = access.key,
            key = mutableStateOf(access.key),
            defaultLabel = label,
            label = mutableStateOf(label)
        )
    }
}