package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.file.AESDirectoryOverview
import com.simplespace.android.lib.simple.fs.aes.file.AESFileOverview
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition

class AESContentOverview (
    val raw: FileNamesWithDirectoryPartition,
    val encryptedDirectories: List<AESDirectoryOverview>,
    val encryptedFiles: List<AESFileOverview>
)