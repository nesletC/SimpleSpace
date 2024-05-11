package com.simplespace.android.lib.simple.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.simplespace.android.lib.standard.permission.PermissionDefinition
import com.simplespace.android.lib.standard.permission.Permissions

abstract class SimplePermissions<T: Enum<T>>(
    vararg definitions: Pair<T, PermissionDefinition>,
) : Permissions<T>(
    definitions = definitions
){

    private var requests : SimplePermissionRequests<T>? = null

    @Composable
    fun rememberRequests(
        update: Boolean = false,
    ) : SimplePermissionRequests<T> {

        if (update || requests == null)
            requests = simplePermissionRequestManager(this)

        return remember{
            requests!!
        }
    }
}