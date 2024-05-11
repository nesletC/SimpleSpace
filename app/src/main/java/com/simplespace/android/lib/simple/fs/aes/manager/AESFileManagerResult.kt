package com.simplespace.android.lib.simple.fs.aes.manager

data class AESFileManagerResult(
    val exception: AESFileManagerException? = null,
    val activeKeyUsed: Boolean = false,
    val isNothingOpenedUpdated: Boolean = false,
    val isInRootDirectoryUpdated: Boolean = false,
    val directoryUpdated: Boolean = false,
    val detailsUpdated: Boolean = false,
    val selectionUpdated: Boolean = false,
    val temporarySelectionUpdated: Boolean = false,
)