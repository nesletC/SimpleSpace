package com.simplespace.android.lib.simple.storage

import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.standard.storage.MainRootDirectoryPaths
import com.simplespace.android.lib.standard.storage.Storages

class SimpleMainRootDirectories (
    storageRootDirectoriesPaths: MainRootDirectoryPaths
){

    fun protected() = simpleMutableFileOf(protectedRootDirectoryIndex)

    fun app() = appRootDirectoryIndex?.let { simpleMutableFileOf(it) }

    val protectedRootDirectoryIndex =
        Storages.indexOf(storageRootDirectoriesPaths.protected)

    val appRootDirectoryIndex = storageRootDirectoriesPaths.app?.let {
        Storages.indexOf(it)
    }
}