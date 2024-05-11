package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.standard.io.file.FileIndicesWithDirectoryPartition

class AESContentDataIndices (
    val raw: FileIndicesWithDirectoryPartition = FileIndicesWithDirectoryPartition(),
    val aes: FileIndicesWithDirectoryPartition = FileIndicesWithDirectoryPartition(),
)