package com.simplespace.android.lib.standard.chain.reader

import com.simplespace.android.lib.standard.array.copyOfLength
import com.simplespace.android.lib.standard.inline.but
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

// usage example
// val asdf = File("asdf").read{
//    while(readable) {
//        val result = read(3)
//        skip(1)
//    }
//}
// the input stream closes automatically

fun ByteArray.chainReader() : BasicChainReaderSkippable<ByteArray> = ByteArrayReader(this)

inline fun <T>ByteArray.read(block: BasicChainReaderSkippable<ByteArray>.() -> T) = chainReader().block()

fun IntArray.chainReader() : BasicChainReaderSkippable<IntArray> = IntArrayReader(this)

inline fun <T>IntArray.read(block: BasicChainReaderSkippable<IntArray>.() -> T) = chainReader().block()

fun CharArray.chainReader() : BasicChainReaderSkippable<CharArray> = CharArrayReader(this)

inline fun <T>CharArray.read(block: BasicChainReaderSkippable<CharArray>.() -> T) = chainReader().block()

fun <T>File.readIt(block: (BasicStreamReaderSkippable) -> T) : T = read(block)

fun <T>File.read(block: BasicStreamReaderSkippable.() -> T) : T = inputStream().read(
    chainLength = length(), block = block
)

fun File.chainReader() : BasicStreamReaderSkippable =
    inputStream().chainReader(chainLength = length())

fun <T>FileInputStream.readIt(chainLength: Long, block: (BasicStreamReaderSkippable) -> T) : T=

    read(chainLength = chainLength, block = block)

fun <T>FileInputStream.read(chainLength: Long, block: BasicStreamReaderSkippable.() -> T) : T =

    chainReader(chainLength).run{

        block().but {
            if (isReadable)
                close()
        }
    }

fun FileInputStream.chainReader(chainLength: Long) : BasicStreamReaderSkippable =
    FileInputStreamChainReader(chainLength = chainLength, inputStream = this)



fun <T>InputStream.readIt(chainLength: Long, block: (BasicStreamReader) -> T) : T =
    read(chainLength = chainLength, block = block)

fun <T>InputStream.read(chainLength: Long, block: BasicStreamReader.() -> T) =

    chainReader(chainLength).run{

        block().but{

            if (isReadable)
                close()
        }
    }

fun InputStream.chainReader(relevantBlockLength: Long) : BasicStreamReader =
    InputStreamChainReader(relevantBlockLength, this)


// ________________________________________________________________________________


private class ByteArrayReader(
    private val array: ByteArray
) : BaseArrayReader<ByteArray>(array.size.toLong()) {

    override fun readFinal(length: Int): ByteArray {
        return array.copyOfLength(position.toInt(), length)
    }
}

private class IntArrayReader(
    private val array: IntArray
) : BaseArrayReader<IntArray>(array.size.toLong()) {

    override fun readFinal(length: Int): IntArray {
        return array.copyOfLength(position.toInt(), length)
    }
}

private class CharArrayReader(
    private val array: CharArray
) : BaseArrayReader<CharArray>(array.size.toLong()) {

    override fun readFinal(length: Int): CharArray {
        return array.copyOfLength(position.toInt(), length)
    }
}

private abstract class BaseArrayReader<T>(
    size: Long
) : BasicChainReaderSkippable<T>, BaseChainReaderSkippable, BaseChainReader<T>(size) {

    override fun skipFinal(length: Long) {}

    override fun onEndReached() {}

    override fun skip(length: Long) {
        super.skip(length)
    }
}

private class FileInputStreamChainReader(
    chainLength: Long,
    override val inputStream: FileInputStream
)
    : BasicStreamReaderSkippable,
    BaseChainReaderSkippable,
    BaseFileInputStreamChainReader(chainLength, inputStream)
{

    override fun skipFinal(length: Long) {
        inputStream.channel.position(position)
    }

    override fun skip(length: Long) {
        super.skip(length)
    }
}

private class InputStreamChainReader (
    chainLength: Long,
    inputStream: InputStream,
)
    : BasicStreamReader, BaseInputStreamChainReader(chainLength, inputStream)

private abstract  class BaseFileInputStreamChainReader(
    chainLength: Long,
    override val inputStream: FileInputStream
) : BaseInputStreamChainReader(chainLength, inputStream)

private abstract class BaseInputStreamChainReader (
    chainLength: Long,
    open val inputStream: InputStream,
)
    : BasicStreamReader, BaseChainReader<ByteArray>(chainLength)
{
    override fun readFinal(length: Int): ByteArray {
        val result = ByteArray(length)
        inputStream.read(result)
        return result
    }

    override fun onEndReached() {
        close()
    }

    override fun close() {
        inputStream.close()
    }
}

private interface BaseChainReaderSkippable {

    val chainLength: Long
    fun skipFinal(length: Long)
    var position: Long
    var isReadable: Boolean
    fun onEndReached()

    fun skip(length: Long) {

        position += length
        if (position >= chainLength) {

            isReadable = false
            onEndReached()
        }
        else
            skipFinal(length)
    }
}

private abstract class BaseChainReader<T> (
    val chainLength: Long
): BasicChainReader<T> {

    final override var isReadable: Boolean = true

    var position = 0L

    override fun read(length: Int) : T {

        val finalLength =
            if (position + length >= chainLength) {

                isReadable = false

                (chainLength - position).toInt()
            }
            else
                length

        val result = readFinal(finalLength)

        position += length

        if (!isReadable)
            onEndReached()

        return result
    }

    abstract fun readFinal(length: Int) : T

    abstract fun onEndReached()
}