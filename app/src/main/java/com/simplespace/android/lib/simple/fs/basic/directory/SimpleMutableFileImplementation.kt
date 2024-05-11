package com.simplespace.android.lib.simple.fs.basic.directory

import com.simplespace.android.lib.standard.storage.Storages

fun simpleMutableFileOf(
    rootDirectoryPath: String,
    parentList: List<String> = listOf(),
    name: String = "",
) =
    simpleMutableFileOf(
        Storages.indexOf(rootDirectoryPath), parentList, name
    )

fun simpleMutableFileOf(
    rootDirectoryIndex: Int,
    parentList: List<String> = listOf(),
    name: String = ""
) : SimpleMutableFile =
    SimpleMutableFileImplementation(rootDirectoryIndex, parentList, name)

private class SimpleMutableFileImplementation (
    rootDirectoryIndex: Int,
    parentList: List<String> = listOf(),
    name: String = "",
) : SimpleFileMutableBase(rootDirectoryIndex, parentList, name)