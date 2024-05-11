package com.simplespace.android.lib.simple.fs.basic.fileManager

import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.standard.io.file.MutableFileNamesWithDirectoryPartition

sealed class SimpleFileManagerReceiver(
    val fileManagerDirectory: SimpleMutableFile,
) {

    class FileSelection(
        rootDirectory: SimpleMutableFile,
        val fileNames: MutableFileNamesWithDirectoryPartition =
            MutableFileNamesWithDirectoryPartition()
    ) : SimpleFileManagerReceiver(rootDirectory)

    class SelectedDirectory(
        rootDirectory: SimpleMutableFile,
    ) : SimpleFileManagerReceiver(rootDirectory)
}