package com.simplespace.android.lib.simple.fs.basic.fileManager

sealed class SimpleFileManagerEvent {

    class Launch(val receiver: SimpleFileManagerReceiver) : SimpleFileManagerEvent()

    data object DirectoryUpdated : SimpleFileManagerEvent()

    class OnResult(val result: SimpleFileManagerResult) : SimpleFileManagerEvent()

    data object Collected: SimpleFileManagerEvent()
}
