package com.simplespace.android.lib.standard.permission

import com.simplespace.android.lib.standard.android.intents.AndroidIntent

class PermissionDataWithSettings(
    val permissionLocationIntent: AndroidIntent,
    label: String,
    description: String,
    state: (() -> Boolean)? = null
)
    : PermissionData(label, description, state)