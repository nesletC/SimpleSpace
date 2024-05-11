package com.simplespace.android.lib.simple.fs.basic.fileManager

sealed class SimpleFileManagerResult {

    data object Directory : SimpleFileManagerResult()

    class Files(
        val directorySelection: List<Int>,
        val fileSelection: List<Int>,
    ) : SimpleFileManagerResult()

    data object Aborted : SimpleFileManagerResult()
}