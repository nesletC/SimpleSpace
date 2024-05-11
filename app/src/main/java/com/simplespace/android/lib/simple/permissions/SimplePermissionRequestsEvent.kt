package com.simplespace.android.lib.simple.permissions

sealed class SimplePermissionRequestsEvent <T> {

    class Launch<T>(
        val requests: List<T>,
    ) : SimplePermissionRequestsEvent<T>()

    class OnDialogDismissed<T> : SimplePermissionRequestsEvent<T>()

    class OnAbort<T>: SimplePermissionRequestsEvent<T>()
}
