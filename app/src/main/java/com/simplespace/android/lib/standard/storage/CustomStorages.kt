package com.simplespace.android.lib.standard.storage

abstract class CustomStorages<T: Enum<T>> {

    fun register(storage: T, data: CustomStorageData) {

        labels[storage] = data.label
        rootDirectoryIndices[storage] = Storages.indexOf(data.path)
    }

    fun label(storage: T) = labels[storage]

    fun directoryIndex(storage: T) = rootDirectoryIndices[storage]

    private val labels = mutableMapOf<T, String>()

    private val rootDirectoryIndices = mutableMapOf<T, Int>()
}