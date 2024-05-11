package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.directory.AESContent

data class AESFileManagerState(
    val name: String = "",
    val content: AESContent = AESContent(),
    val isNothing: Boolean = true,
    val isRootDirectory: Boolean = true,
    val details: AESFileManagerFileDetails? = null,
    val temporarySelection: AESFileManagerSelectionIndices? = null,
    val selectionDescription: AESFileManagerSelectionDescription? = null,
)
