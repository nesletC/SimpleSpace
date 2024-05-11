package com.simplespace.android.spaces.presentation

import com.simplespace.android.spaces.data.SpaceAuthentication
import com.simplespace.android.spaces.model.SpaceDirectoryReference

sealed class SpaceEvent {

    data object OnBaseLaunched: SpaceEvent()

    class OnDirectorySelected (
        val sessionIndex: Int,
        val directory: SpaceDirectoryReference,
    ) : SpaceEvent()

    class StoppingSession(
        val sessionIndex: Int
    ) : SpaceEvent()

    data object UserActivity: SpaceEvent()

    sealed class LaunchingSpaceEdit : SpaceEvent() {

        data object New: LaunchingSpaceEdit()

        class Update( val sessionIndex: Int ) : LaunchingSpaceEdit()
    }

    class LaunchingAuthentication(val action: SpaceAuthentication.Action ) : SpaceEvent()

    data object OnAuthenticatedActionSuccessful : SpaceEvent()
}