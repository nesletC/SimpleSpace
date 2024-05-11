package com.simplespace.android.lib.standard.permission

abstract class PermissionData (
    val label: String,
    val description: String,
    val state: (() -> Boolean)?
)