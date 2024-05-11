package com.simplespace.android.lib.simple.fs.basic.directory

import com.simplespace.android.lib.standard.io.file.FileIndicesWithDirectoryPartition

class DirectoryContentMoveError(
    val fileErrorCount: Int,
    val indices: FileIndicesWithDirectoryPartition,
)