package com.simplespace.android.lib.simple.fs.aes.manager

sealed class AESFileManagerException {

    sealed class File : AESFileManagerException() {

        data object Rename: File()
    }

    sealed class EncryptedFile : AESFileManagerException() {

        data object FileName: EncryptedFile()

        data object Name: EncryptedFile()

        data object Key: EncryptedFile()
    }
}