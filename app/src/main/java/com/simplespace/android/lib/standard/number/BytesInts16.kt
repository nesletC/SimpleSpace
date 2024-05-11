package com.simplespace.android.lib.standard.number

import com.simplespace.android.lib.standard.array.init

object BytesInts16 : BytesInts {

    // to convert a ByteArray to an IntArray with values between 0 and 15

    override operator fun invoke(bytes: ByteArray) : IntArray {

        return IntArray(bytes.size * 2).init {
                set ->

            for (index in bytes.indices) {

                val byte = bytes[index].toUByte().toInt()

                set(byte / 16)
                set(byte % 16)
            }
        }
    }

    // to convert an IntArray with values between 0 and 15 to a ByteArray
    // the size of the IntArray must be divisible by 2

    override fun bytes(intArray: IntArray) : ByteArray {

        val blockCount = intArray.size / 2

        return ByteArray(blockCount).init { set ->

            for (blockIndex in 0..<blockCount) {

                val intArrayIndex = blockIndex * 2

                set(
                    (
                        intArray[intArrayIndex] * 16 + intArray[intArrayIndex + 1]
                    )
                        .toByte()
                )
            }
        }
    }
}