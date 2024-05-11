package com.simplespace.android.lib.standard.chain.writer

import com.simplespace.android.lib.standard.array.setAll


fun ByteArray.chainWriter() : BasicArrayWriter<ByteArray> = ByteArrayWriter (this)

inline fun <T>ByteArray.write(block: BasicArrayWriter<ByteArray>.() -> T) : T =
    chainWriter().block()


fun IntArray.chainWriter() : BasicArrayWriter<IntArray> = IntArrayWriter (this)

inline fun <T>IntArray.write(block: BasicArrayWriter<IntArray>.() -> T) : T =
    chainWriter().block()


fun CharArray.chainWriter() : BasicArrayWriter<CharArray> = CharArrayWriter (this)

inline fun <T>CharArray.write(block: BasicArrayWriter<CharArray>.() -> T) : T =
    chainWriter().block()


private class ByteArrayWriter (
    private val byteArray: ByteArray,
) : ArrayWriter<ByteArray>(byteArray.size) {

    override fun writeFinal(array: ByteArray): Int =
        byteArray.setAll(array, position)
}


private class IntArrayWriter (
    private val intArray: IntArray,
) : ArrayWriter<IntArray>(intArray.size) {

    override fun writeFinal(array: IntArray): Int =
        intArray.setAll(array, position)
}

private class CharArrayWriter (
    private val charArray: CharArray,
) : ArrayWriter<CharArray>(charArray.size) {

    override fun writeFinal(array: CharArray): Int =
        charArray.setAll(array, position)
}

private abstract class ArrayWriter<T>(
    val size: Int
) : BasicArrayWriter<T> {

    abstract fun writeFinal(array: T) : Int

    var position = 0

    var writeableValue = true

    override fun write(array: T) : Int = writeFinal(array).also{

        position += it

        if (position == size)
            writeableValue = false
    }

    override val writeable: Boolean
        get() = writeableValue

    override fun skip(length: Int) {
        position += length
        if ( position >= size )
            writeableValue = false
    }
}