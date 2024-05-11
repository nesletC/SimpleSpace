package com.simplespace.android.lib.standard.io.file

class MutableFileNamesWithDirectoryPartition(
    override val directories: MutableList<String> = mutableListOf(),
    override val files: MutableList<String> = mutableListOf(),
) : FileNamesWithDirectoryPartition