package com.simplespace.android.lib.standard.storage

import android.os.Environment
import java.io.File

object Storages {

    fun state(index: Int) = states[index]

    fun path(index: Int) = paths[index]

    fun indexOf(path: String) : Int {

        val index = paths.indexOf(path)

        return if(index == -1) {

            paths.add(path)

            states.add(getStorageState(path))

            paths.lastIndex
        }
        else
            index
    }

    private fun getStorageState(path: String) : StorageState {

        val state = Environment.getExternalStorageState(File(path))

        return StorageState(
            isIO = Environment.MEDIA_MOUNTED == state,
            isReadOnly = Environment.MEDIA_MOUNTED_READ_ONLY == state
        )
    }

    private val paths = mutableListOf<String>()

    private val states = mutableListOf<StorageState>()
}