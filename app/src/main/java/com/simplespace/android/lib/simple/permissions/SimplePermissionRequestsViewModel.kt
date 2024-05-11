package com.simplespace.android.lib.simple.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.lib.simple.permissions.indirectRequestDialogs.SimpleIndirectPermissionTexts
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.iterable.list.immutableListOf
import com.simplespace.android.lib.standard.iterable.list.list
import com.simplespace.android.lib.standard.iterable.list.toImmutableList
import com.simplespace.android.lib.standard.permission.PermissionState
import com.simplespace.android.lib.standard.permission.Permissions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimplePermissionRequestsViewModel<T : Enum<T>> (
    val permissions: Permissions<T>
): ViewModel() {

    private val allowedPermissionLocationIntents =
        mutableMapOf<AndroidIntent, MutableList<T>>()

    private var requestedIndirectPermissionLocationIntents = mutableListOf<AndroidIntent>()

    private var focusedRequestedPermissionLocationIntent = 0

    init{

        permissions.data.forEach{

            val intent = it.value.permissionLocationIntent

            val list = allowedPermissionLocationIntents[intent]

            if (list == null)
                allowedPermissionLocationIntents[intent] = mutableListOf(it.key)
            else
                list.add(it.key)
        }
    }

    private val _state =
        MutableStateFlow(SimplePermissionRequestsState())

    val state = _state.asStateFlow()

    private val _onDirectRequest = MutableSharedFlow<List<T>>()

    val onDirectRequest = _onDirectRequest.asSharedFlow()

    fun onEvent(event: SimplePermissionRequestsEvent<T>) {

        when (event) {
            is SimplePermissionRequestsEvent.Launch -> {

                viewModelScope.launch{

                    requestedIndirectPermissionLocationIntents.clear()

                    focusedRequestedPermissionLocationIntent = 0

                    val directRequests = mutableListOf<T>()

                    val requestedIndirectRequestPermissionDialogsToShow =
                        list<MutableList<SimpleIndirectPermissionTexts>> {

                            allowedPermissionLocationIntents.forEach {
                                    (intent, permissionsOfSettings) ->

                                for (permission in permissionsOfSettings) {

                                    if (event.requests.contains(permission)) {

                                        val data = permissions.data[permission]!!

                                        permissions.updateState(permission)

                                        val state = permissions.state[permission]!!

                                        if (state == PermissionState.GRANTED)
                                            continue
                                        else if (

                                            data.isDirectRequest &&
                                            state == PermissionState.NOT_REQUESTED
                                        )
                                            directRequests.add(permission)
                                        else {

                                            val textsToAdd =
                                                SimpleIndirectPermissionTexts(
                                                    label = data.label,
                                                    description = data.description
                                                )

                                            val index =
                                                requestedIndirectPermissionLocationIntents
                                                    .indexOf(intent)

                                            if (index == -1) {
                                                requestedIndirectPermissionLocationIntents
                                                    .add(intent)
                                                add(mutableListOf(textsToAdd))
                                            }
                                            else
                                                get(index).add(textsToAdd)
                                        }
                                    }
                                }
                            }
                        }

                    _onDirectRequest.emit(directRequests)

                    _state.update { state ->

                        state.copy(
                            requestedIntents = requestedIndirectPermissionLocationIntents
                                .toImmutableList(),
                            entries = requestedIndirectRequestPermissionDialogsToShow.map {
                                it.toImmutableList()
                            }.toImmutableList(),
                            visibilities = SimplePermissionRequestsStateVisibilities(
                                visible = true,
                                visibleIntent = requestedIndirectPermissionLocationIntents[0]
                            )
                        )
                    }
                }
            }

            is SimplePermissionRequestsEvent.OnDialogDismissed -> {

                viewModelScope.launch {

                    focusedRequestedPermissionLocationIntent++

                    _state.update {

                        it.copy(
                            visibilities =
                            if (
                                focusedRequestedPermissionLocationIntent ==
                                requestedIndirectPermissionLocationIntents.size
                            )
                                SimplePermissionRequestsStateVisibilities()
                            else
                                it.visibilities.copy(
                                    visibleIntent = requestedIndirectPermissionLocationIntents[
                                        focusedRequestedPermissionLocationIntent
                                    ]
                                )
                        )
                    }
                }
            }

            is SimplePermissionRequestsEvent.OnAbort -> {

                _state.update {
                    it.copy(
                        requestedIntents = immutableListOf(),
                        entries = immutableListOf(),
                        visibilities = SimplePermissionRequestsStateVisibilities()
                    )
                }
            }
        }
    }
}