package com.simplespace.android.lib.simple.fs.basic.file

import com.simplespace.android.lib.standard.storage.Storages
import java.io.File

fun simpleFileOf(
    rootDirectoryPath: String,
    parentList: List<String> = listOf(),
    name: String = "",
) = simpleFileOf(
        Storages.indexOf(rootDirectoryPath), parentList, name
    )

fun simpleFileOf(
    rootDirectoryIndex: Int,
    parentList: List<String> = listOf(),
    name: String = ""
) : SimpleFile =
    SimpleFileImplementation(rootDirectoryIndex, parentList, name)

private class SimpleFileImplementation(
    override val rootDirectoryIndex: Int,
    override val parentList: List<String>,
    override val name: String,
) : SimpleFile {

    override val rootDirectoryPath: String = Storages.path(rootDirectoryIndex)

    override val file get() = _file?: File(absoluteFullPath()).also { _file = it }

    private var _file: File? = null

    override fun copy(): SimpleFile = simpleFileOf(rootDirectoryIndex, parentList, name)
}