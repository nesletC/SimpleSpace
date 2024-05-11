package com.simplespace.android.lib.standard.storage

sealed class Storage {

    class Custom(
        val index: Int,
    ) : Storage() {

        fun path() = Storages.path(index)
        fun state() = Storages.state(index)
    }

    sealed class Main: Storage() {

        data object Protected: Main()

        sealed class Open: Main() {

            data object Public : Open()
            data object App : Open()
        }
    }
}