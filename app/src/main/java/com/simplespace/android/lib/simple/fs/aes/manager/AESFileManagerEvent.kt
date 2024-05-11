package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition

sealed class AESFileManagerEvent {

    data object OpenNothing: AESFileManagerEvent()

    class OpenRoot(
        val file: SimpleFile,
        val meta: AESMeta,
    ) : AESFileManagerEvent()

    class OpenChild(
        val index: Int
    ) : AESFileManagerEvent()

    data object OpenParent : AESFileManagerEvent()

    data object DeleteDetails: AESFileManagerEvent()

    sealed class EncryptedFile(
        val isDirectory: Boolean,
        val index: Int,
    ) : AESFileManagerEvent() {

        class Rename(
            isDirectory: Boolean,
            index: Int,
            val newName: String
        ) : EncryptedFile(isDirectory, index)

        class ChangeKey(
            isDirectory: Boolean,
            index: Int,
            val newKey: ByteArray
        ) : EncryptedFile(isDirectory, index)

        class ChangeFileName(
            isDirectory: Boolean,
            index: Int,
            val newName: String
        ) : EncryptedFile(isDirectory, index)

        class ShowDetails(
            isDirectory: Boolean,
            index: Int,
        ) : EncryptedFile(isDirectory, index)
    }

    sealed class File(
        val isDirectory: Boolean,
        val index: Int,
    ) : AESFileManagerEvent() {

        class Rename(
            isDirectory: Boolean,
            index: Int,
            val newName: String
        ) : File(isDirectory, index)

        class ShowDetails(
            isDirectory: Boolean,
            index: Int,
        ) : File(isDirectory, index)
    }

    sealed class TemporarySelection : AESFileManagerEvent() {

        data object Init: TemporarySelection()

        data object All: TemporarySelection()

        data object None: TemporarySelection()

        class Switch(
            val isEncrypted: Boolean,
            val isDirectory: Boolean,
            val index: Int
        ) : TemporarySelection()

        data object Abort: TemporarySelection()
    }

    data object CancelingSelection : AESFileManagerEvent()

    data object SavingSelection : AESFileManagerEvent()

    class ExecutingSelectionAction (
        val action: AESFileManagerSelectionAction
    ) : AESFileManagerEvent()

    class Export(
        val target: SimpleMutableFile,
        val action: AESFileManagerSelectionAction.Move
    ) : AESFileManagerEvent()

    class Import(
        val source: SimpleMutableFile,
        val action: AESFileManagerSelectionAction.Move,
        val selection: FileNamesWithDirectoryPartition,
        val encrypt: Boolean,
    ) : AESFileManagerEvent()
}