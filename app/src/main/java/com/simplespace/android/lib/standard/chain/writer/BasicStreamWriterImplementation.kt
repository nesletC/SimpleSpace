package com.simplespace.android.lib.standard.chain.writer

import com.simplespace.android.lib.standard.inline.but
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun OutputStream.chainWriter() : BasicStreamWriter = StreamWriter(this)

inline fun <T>OutputStream.write(block: BasicStreamWriter.() -> T) : T =
    chainWriter().block().but {
        close()
    }


fun File.chainWriter() : BasicStreamWriterSkippable = FileStreamWriter(outputStream())

inline fun <T>File.writeIt(block: (BasicStreamWriterSkippable) -> T) : T = write(block)

inline fun <T>File.write(block: BasicStreamWriterSkippable.() -> T) : T =
    chainWriter().run{
        block().but{
            close()
        }
    }


private class StreamWriter(outputStream: OutputStream) :
    BasicStreamWriter, BaseStreamWriter(outputStream)

private class FileStreamWriter(
    override val outputStream: FileOutputStream
) : BasicStreamWriterSkippable, BaseStreamWriter(outputStream) {

    override fun skip(length: Int) {
        position += length
        (outputStream).channel.position(position)
    }
}

private abstract class BaseStreamWriter(
    open val outputStream: OutputStream
) : BasicStreamWriter {

    var position = 0L

    override fun write(array: ByteArray) : Int {
        outputStream.write(array)

        return array.size.also {
            position += it
        }
    }

    override fun close() {
        outputStream.close()
    }
}