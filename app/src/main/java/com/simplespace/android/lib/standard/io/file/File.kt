package com.simplespace.android.lib.standard.io.file

import com.simplespace.android.lib.standard.io.file.suffix.hasSuffix
import com.simplespace.android.lib.standard.io.path.move
import com.simplespace.android.lib.standard.tryFunctions.tryBoolean
import java.io.File


fun File.getMutableDirectoryContent() : MutableFileNamesWithDirectoryPartition {

    val directories = mutableListOf<String>()
    val files = mutableListOf<String>()

    val fileList = list()!!

    fileList.forEach{
        if (it.hasSuffix())
            files.add(it)
        else
            directories.add(it)
    }

    return MutableFileNamesWithDirectoryPartition(
        directories, files,
    )
}

fun File.getDirectoryContent() : FileNamesWithDirectoryPartition = getMutableDirectoryContent()

fun File.mkdirSafely() : Boolean =
    if (!exists())
        tryBoolean {
            mkdir()
        }
    else false

fun File.move(target: File, copy: Boolean, overwrite: Boolean) {

    if (copy)
        copyTo(target, overwrite)
    else
        toPath().move(target.toPath(), overwrite)
}