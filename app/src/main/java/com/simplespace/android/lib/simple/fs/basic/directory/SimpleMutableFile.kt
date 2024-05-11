package com.simplespace.android.lib.simple.fs.basic.directory

import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.MutableFileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.storage.Storages

interface SimpleMutableFile : SimpleFile {

    val content: FileNamesWithDirectoryPartition

    fun getLevel() = if (name.isBlank()) -1 else parentList.size

    fun getMutableContent(): MutableFileNamesWithDirectoryPartition

    fun isEmpty(): Boolean

    fun changeName(newName: String)

    fun parent(level: Int = 0)

    fun child(childName: String)

    fun openRoot()

    fun openRoot(
        rootDirectoryIndex: Int
    ) = openRoot(
        rootDirectoryPath = Storages.path(rootDirectoryIndex),
        rootDirectoryIndex = rootDirectoryIndex
    )

    fun openRoot(
        rootDirectoryPath: String
    ) = openRoot(
        rootDirectoryPath = rootDirectoryPath,
        rootDirectoryIndex = Storages.indexOf(rootDirectoryPath)
    )

    fun openRoot(
        rootDirectoryPath: String,
        rootDirectoryIndex: Int,
    )

    fun open(file: SimpleFile)

    fun getMutableParentList() = parentList.toMutableList()
}

private const val ALTERNATIVE_FILE_NAME_SEPARATOR = "-"