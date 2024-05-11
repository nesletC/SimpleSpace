package com.simplespace.android.lib.simple.fs.aes.manager

sealed class AESFileManagerSelectionAction {

    class Encrypt(
        val overwrite: Boolean,
        val copy: Boolean,
    ): AESFileManagerSelectionAction()

    class Decrypt(
        val overwrite: Boolean,
        val copy: Boolean,
    ): AESFileManagerSelectionAction()

    class Move(
        val copy: Boolean,
        val overwrite: Boolean,
    ) : AESFileManagerSelectionAction()

    data object Delete: AESFileManagerSelectionAction()
}