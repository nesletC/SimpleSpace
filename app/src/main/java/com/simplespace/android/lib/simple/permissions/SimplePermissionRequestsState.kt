package com.simplespace.android.lib.simple.permissions

import androidx.compose.runtime.Immutable
import com.simplespace.android.lib.simple.permissions.indirectRequestDialogs.SimpleIndirectPermissionTexts
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.iterable.list.ImmutableList
import com.simplespace.android.lib.standard.iterable.list.immutableListOf

@Immutable
data class SimplePermissionRequestsState (
    val requestedIntents: ImmutableList<AndroidIntent> = immutableListOf(),
    val entries: ImmutableList<ImmutableList<SimpleIndirectPermissionTexts>> = immutableListOf(),
    val visibilities: SimplePermissionRequestsStateVisibilities =
        SimplePermissionRequestsStateVisibilities()
)