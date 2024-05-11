package com.simplespace.android.lib.standard.permission

sealed class PermissionDefinition(
    val data: PermissionDataWithSettings,
) {
    class DirectRequest(

        val id: String,

        data: PermissionDataWithSettings,

        ): PermissionDefinition(data)

    class IndirectRequest(

        data: PermissionDataWithSettings,

        ): PermissionDefinition(data)
}