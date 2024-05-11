package com.simplespace.android.lib.standard.permission

import com.simplespace.android.lib.standard.android.intents.AndroidIntent

class PermissionDataFinal (
    val permissionLocationIntent: AndroidIntent,
    val isDirectRequest: Boolean,
    label: String,
    description: String,
    state: (() -> Boolean)? = null
)
    : PermissionData(label, description, state)