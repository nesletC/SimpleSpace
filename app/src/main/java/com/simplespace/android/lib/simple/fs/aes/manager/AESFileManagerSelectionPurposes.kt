package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose.COPY
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose.DECRYPT
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose.DELETE
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose.ENCRYPT
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose.MOVE
import com.simplespace.android.lib.standard.iterable.list.getIndicesMap

object AESFileManagerSelectionPurposes {

    val indices =
        AESFileManagerSelectionPurpose.entries.getIndicesMap()

    val labels = mapOf(
        ENCRYPT to "Encrypt",
        DECRYPT to "Decrypt",
        COPY to "Copy",
        MOVE to "Move",
        DELETE to "Delete",
    )
}