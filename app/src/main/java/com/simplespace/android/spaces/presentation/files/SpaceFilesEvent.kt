package com.simplespace.android.spaces.presentation.files

import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionAction
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerReceiver

sealed class SpaceFilesEvent {

    class SelectionPurposeUpdated(val purpose: AESFileManagerSelectionPurpose?) : SpaceFilesEvent()

    class SelectionActionUpdated(val action: AESFileManagerSelectionAction?) : SpaceFilesEvent()

    sealed class Import : SpaceFilesEvent() {

        class Request(
            val receiver: SimpleFileManagerReceiver.FileSelection
        ) : Import()

        data object OnResult: Import()

        data object Aborted: Import()

        class Action(
            val action: AESFileManagerSelectionAction.Move,
            val encrypt: Boolean
        ) : Import()
    }

    sealed class Export : SpaceFilesEvent() {

        class Request(
            val receiver: SimpleFileManagerReceiver.SelectedDirectory
        ) : Export()

        data object OnResult: Export()

        data object Aborted: Export()

        class Action(
            val action: AESFileManagerSelectionAction.Move,
        ) : Export()
    }
}