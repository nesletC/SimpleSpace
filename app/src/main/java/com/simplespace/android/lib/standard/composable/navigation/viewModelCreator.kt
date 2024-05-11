package com.simplespace.android.lib.standard.composable.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> viewModelCreator(
    viewModelStoreOwner: ViewModelStoreOwner? = null,
    noinline implementation: () -> T,
) : T {
    return viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return implementation() as T
            }
        },
        viewModelStoreOwner = viewModelStoreOwner ?: LocalViewModelStoreOwner.current!!
    )
}