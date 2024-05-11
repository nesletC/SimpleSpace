package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.fileNamesWithDirectoryPartition

class AESContentFileNames (
    val raw: FileNamesWithDirectoryPartition = fileNamesWithDirectoryPartition(),
    val aes: FileNamesWithDirectoryPartition = fileNamesWithDirectoryPartition(),
)