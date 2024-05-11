package com.simplespace.android.lib.simple.fs.basic.directory.util

import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile

fun SimpleMutableFile.toUniqueFile() {

    val originalName = name
    var index = 0

    while(file.exists()) {

        index++

        changeName(originalName + ALTERNATIVE_FILE_NAME_SEPARATOR + index.toString())
    }
}

private const val ALTERNATIVE_FILE_NAME_SEPARATOR = "-"