package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.file.AESData

sealed class AESFileManagerFileDetails (
    val isDirectory: Boolean
){

    class AES(
        isDirectory: Boolean,
        val data: AESData
    ) : AESFileManagerFileDetails(isDirectory)

    class Raw(
        isDirectory: Boolean,
        val name: String,
    ) : AESFileManagerFileDetails(isDirectory)
}