package com.simplespace.android.lib.simple.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplespace.android.app.App
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.permissions.indirectRequestDialogs.SimpleIndirectPermissionRequestDialogs
import com.simplespace.android.lib.standard.composable.navigation.viewModelCreator

fun <T : Enum<T>>simplePermissionRequestManager(
    parent: SimplePermissions<T>,
) : SimplePermissionRequests<T> =
    SimplePermissionRequestsImplementation(parent)

private class SimplePermissionRequestsImplementation<T : Enum<T>>(
    private val permissions: SimplePermissions<T>,
) : SimplePermissionRequests<T> {

    @Composable
    override fun Dialogs(scope: AppScreenScope) {

        val viewModel = viewModelCreator {
            SimplePermissionRequestsViewModel(permissions)
        }

        onRequest = {

            viewModel.onEvent(SimplePermissionRequestsEvent.Launch(it))
        }

        val state = viewModel.state.collectAsStateWithLifecycle()

        val launchActivityResult = permissions.multipleResultsLauncher()

        scope.SimpleIndirectPermissionRequestDialogs(
            state = state,
            dismissDialog = {
                viewModel.onEvent(SimplePermissionRequestsEvent.OnDialogDismissed())
            },
            openSettings = {
                App.launch(state.value.visibilities.visibleIntent)
            },
            abort = {
                viewModel.onEvent(SimplePermissionRequestsEvent.OnAbort())
            },
        )

        LaunchedEffect(Unit) {

            viewModel.onDirectRequest.collect {

                launchActivityResult(it)
            }
        }
    }

    override fun launch(permissionsToRequest: List<T>) {
        onRequest?.invoke(permissionsToRequest)
    }

    private var onRequest: ((List<T>) -> Unit) ? = null
}