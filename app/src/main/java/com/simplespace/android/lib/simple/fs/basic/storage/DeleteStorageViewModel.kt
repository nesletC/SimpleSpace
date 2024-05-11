package com.simplespace.android.lib.simple.fs.basic.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.app.App
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DeleteStorageViewModel : ViewModel(){

    private val _deletionComplete = MutableSharedFlow<Unit>()

    val deletionComplete = _deletionComplete.asSharedFlow()

    private val internal = App.mainRootDirectories.protected().file

    private val externalApp = App.mainRootDirectories.app()?.file

    operator fun invoke() {

        viewModelScope.launch {

            internal.deleteRecursively()
            externalApp?.deleteRecursively()
        }
    }
}