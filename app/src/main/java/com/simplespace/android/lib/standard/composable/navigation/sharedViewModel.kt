package com.simplespace.android.lib.standard.composable.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    noinline viewModelCreatorFunction: () -> T,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModelCreator(
        implementation = viewModelCreatorFunction
    )
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModelCreator(
        viewModelStoreOwner = parentEntry,
        implementation = viewModelCreatorFunction
    )
}