package com.simplespace.android.spaces.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.standard.iterable.map.mapValuesOnly
import com.simplespace.android.spaces.data.SpaceAuthentication
import com.simplespace.android.spaces.data.SpaceSessions
import com.simplespace.android.spaces.model.SpaceDirectoryReference
import com.simplespace.android.spaces.model.SpaceMeta
import com.simplespace.android.spaces.model.SpaceOverview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpacesViewModel: ViewModel() {

    private val _onError = MutableSharedFlow<SpaceError>()

    val onError = _onError.asSharedFlow()

    private val _setRootDirectory =
        MutableSharedFlow<AESFileManagerEvent.OpenRoot>()

    val setRootDirectory = _setRootDirectory.asSharedFlow()

    private val _setNothing = MutableSharedFlow<Unit>()

    val setNothing = _setNothing.asSharedFlow()

    private val _openBase = MutableSharedFlow<Unit>()

    val openBase =_openBase.asSharedFlow()

    private val _openAuthentication = MutableSharedFlow<Unit>()

    val openAuthentication =
        _openAuthentication.asSharedFlow()

    private val _openSpaceEdit = MutableSharedFlow<Unit>()

    val openSpaceEdit = _openSpaceEdit.asSharedFlow()


    private val _state = MutableStateFlow(SpacesUIState())

    val state = _state.asStateFlow()


    // Information essential for screens outside of Base ("SpaceFiles") -----------------------

    var spaceAuthenticationAction: SpaceAuthentication.Action? = null

    var spaceSavingEvent: SpaceEvent.LaunchingSpaceEdit? = null

    // ---------------------------------------------------------------------------------------

    fun onEvent(event: SpaceEvent) {

        viewModelScope.launch {

            when (event) {

                SpaceEvent.OnBaseLaunched -> {
                    spaceAuthenticationAction = null
                    spaceSavingEvent = null
                }

                is SpaceEvent.OnDirectorySelected -> {

                    val selected = SpaceSessions.getMeta(event.sessionIndex)

                    if (selected == null){

                        updateSessions()

                        _onError.emit(SpaceError.SPACE_TIMED_OUT)
                        return@launch
                    }

                    if (_state.value.current != event.sessionIndex) {

                        _state.update {
                            it.copy(
                                current = event.sessionIndex
                            )
                        }
                    }

                    when (event.directory) {

                        is SpaceDirectoryReference.Custom -> {

                            val selectedDirectory =
                                selected.directories[event.directory.index]

                            _setRootDirectory.emit(
                                AESFileManagerEvent.OpenRoot(
                                    file = selectedDirectory.file(),
                                    meta = AESMeta(
                                        realName = selectedDirectory.label,
                                        key = selectedDirectory.access.key
                                    )
                                )
                            )
                        }

                        SpaceDirectoryReference.Protected ->
                            _setRootDirectory.emit(selected.openingRootEvent())

                        else ->
                            _setRootDirectory.emit(
                                AESFileManagerEvent.OpenRoot(
                                    file = selected.appDirectoryFile()!!,
                                    meta = AESMeta(
                                        realName = selected.realName,
                                        key = selected.appDirectory!!.key
                                    )
                                )
                            )
                    }

                    SpaceSessions.resetTimeout(event.sessionIndex)
                }

                SpaceEvent.UserActivity ->
                    state.value.current?.let{
                        SpaceSessions.resetTimeout(it)
                    }

                is SpaceEvent.StoppingSession -> {

                    if (SpaceSessions.stop(event.sessionIndex)) {

                        updateSessions()

                        emitLatestDirectory()
                    }
                }

                SpaceEvent.OnAuthenticatedActionSuccessful -> {

                    _openBase.emit(Unit)

                    val latestState = sessionsUpdate.current()

                    val newestSession = SpaceSessions.newestSession

                    _state.update {
                        SpacesUIState(
                            overview = getOverview(latestState),
                            current = newestSession
                        )
                    }

                    if (newestSession != null)
                        emitLatestDirectory()
                    else
                        _setNothing.emit(Unit)
                }

                is SpaceEvent.LaunchingAuthentication -> {

                    spaceAuthenticationAction = event.action

                    _openAuthentication.emit(Unit)
                }

                is SpaceEvent.LaunchingSpaceEdit -> {

                    spaceSavingEvent = event

                    if (event is SpaceEvent.LaunchingSpaceEdit.Update)
                        SpaceSessions.resetTimeout(event.sessionIndex)

                    _openSpaceEdit.emit(Unit)
                }
            }
        }
    }

    private suspend fun emitLatestDirectory() {

        SpaceSessions.getLatest()?.let{

            _setRootDirectory.emit(it.openingRootEvent())
        }
            ?:_setNothing.emit(Unit)
    }

    private fun SpaceMeta.openingRootEvent() = AESFileManagerEvent.OpenRoot(
        file = protectedDirectoryFile(),
        meta = AESMeta(
            realName = realName,
            key = protectedDirectory.key,
            secretKey = protectedDirectory.secretKey
        )
    )

    private fun updateSessions(sessions: Map<Int, SpaceMeta> = sessionsUpdate.current()) {

        if (sessions.isEmpty())
            _state.update {
                SpacesUIState()
            }
        else
            getOverview(sessions).let{ overview ->

                if ( _state.value.current != null && sessions[_state.value.current] == null)
                    _state.update {
                        SpacesUIState(
                            overview = overview
                        )
                    }
                else
                    _state.update {
                        it.copy(overview = overview)
                    }
            }
    }

    private fun getOverview(sessions: Map<Int, SpaceMeta>) = sessions.mapValuesOnly {

        SpaceOverview(
            name = realName,
            appDirectoryExists = appDirectory != null,
            customDirectories = directories.map {
                it.label
            }
        )
    }

    private val sessionsUpdate =
        SpaceSessions.getUpdates(30000)

    init {

        viewModelScope.launch {

            sessionsUpdate.flow.collect{

                if (it != null)
                    updateSessions(it)
            }
        }
    }
}