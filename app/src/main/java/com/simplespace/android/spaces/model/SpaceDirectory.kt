package com.simplespace.android.spaces.model

import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.simple.fs.basic.file.simpleFileOf
import com.simplespace.android.lib.standard.storage.Storages

class SpaceDirectory(
    val rootDirectoryIndex: Int,
    val parentList: List<String>,
    val label: String,
    val access: SpaceDirectoryAccess,
) {

    fun file() = simpleFileOf(rootDirectoryIndex, parentList, access.fileName)

    fun mutableFile() = simpleMutableFileOf(rootDirectoryIndex, parentList, access.fileName)

    fun saveable() =
        SpaceDirectorySaveable(
            rootDirectoryPath = Storages.path(rootDirectoryIndex),
            parentList = parentList,
            label = label,
            access = access
        )
}