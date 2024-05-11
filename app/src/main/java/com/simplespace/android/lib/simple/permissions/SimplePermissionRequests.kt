package com.simplespace.android.lib.simple.permissions

import androidx.compose.runtime.Composable
import com.simplespace.android.app.lib.screen.AppScreenScope

interface SimplePermissionRequests<T : Enum<T>>{

    fun launch(permissionsToRequest: List<T>)

    @Composable
    fun Dialogs(
        scope: AppScreenScope,
    )
}