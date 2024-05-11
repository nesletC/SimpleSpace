package com.simplespace.android.lib.simple.fs.basic.file

import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.standard.io.file.FilePath
import java.io.File

interface SimpleFile {

    val rootDirectoryIndex: Int
    val rootDirectoryPath: String
    val parentList: List<String>
    val name: String

    val file: File

    fun treeLevel() = parentList.size

    fun equalsFile(other: SimpleFile) =
        rootDirectoryIndex == other.rootDirectoryIndex &&
        fullPath() == other.fullPath()

    fun path(): String = parentList.joinToString(FilePath.SEPARATOR)

    fun fullPath() : String {
        val path = path()
        return if (path.isBlank())
            name
        else
            FilePath.addNullablePath(root = path(), path = name)
    }

    fun absolutePath() : String = FilePath.addNullablePath(root = rootDirectoryPath, path = path())

    fun absoluteFullPath() = FilePath.addNullablePath(root = rootDirectoryPath, path = fullPath())

    fun copy() : SimpleFile = simpleFileOf(
        rootDirectoryIndex, parentList, name
    )

    fun mutableCopy() : SimpleMutableFile = simpleMutableFileOf(
        rootDirectoryIndex, parentList.toList(), name
    )
}