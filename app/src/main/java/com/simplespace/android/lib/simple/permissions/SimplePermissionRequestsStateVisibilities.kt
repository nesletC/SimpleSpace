package com.simplespace.android.lib.simple.permissions

import androidx.compose.runtime.Immutable
import com.simplespace.android.lib.standard.android.intents.AndroidIntent

@Immutable
data class SimplePermissionRequestsStateVisibilities(
    val visible: Boolean = false,
    val visibleIntent: AndroidIntent = AndroidIntent.entries[0],
)
