package com.simplespace.android.spaces.presentation.edit

import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerReceiver
import com.simplespace.android.spaces.model.SpaceDirectoryAccess

sealed class SpaceEditEvent {

    sealed class NewExternalSpaceDirectory : SpaceEditEvent() {

        class Request(
            val receiver: SimpleFileManagerReceiver.SelectedDirectory
        ) : NewExternalSpaceDirectory()

        data object OnResult: NewExternalSpaceDirectory()

        data object Aborted: NewExternalSpaceDirectory()
    }

    class Save(
        val spaceName : String,
        val maxTimeoutInMinutes: Int,
        val protectedDirectory: SpaceDirectoryAccess,
        val appDirectory: SpaceDirectoryAccess?
    ): SpaceEditEvent()
}
