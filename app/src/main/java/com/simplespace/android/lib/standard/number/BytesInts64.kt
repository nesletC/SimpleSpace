package com.simplespace.android.lib.standard.number

import com.simplespace.android.lib.standard.array.init

object BytesInts64 : BytesInts{

    // to convert a ByteArray to an IntArray with values between 0 and 63
    // the size of the ByteArray must be divisible by 3

    override operator fun invoke(bytes: ByteArray) : IntArray {

        val blockCount = bytes.size / 3

        return IntArray(blockCount * 4).init {
            set ->

            for (blockIndex in 0..<blockCount) {

                val byteArrayIndex = blockIndex * 3

                val byte0 = bytes[byteArrayIndex].toUByte().toInt()
                val byte1 = bytes[byteArrayIndex + 1].toUByte().toInt()
                val byte2 = bytes[byteArrayIndex + 2].toUByte().toInt()

                set(
                    byte0.shr(2)
                )

                set (
                    (byte0.shl(4) or byte1.shr(4)) and 63
                )

                set (
                    (byte1.shl(2) or byte2.shr(6)) and 63
                )

                set (
                    byte2 and 63
                )
            }
        }
    }

    // to convert an IntArray with values between 0 and 63 to a ByteArray
    // the size of the IntArray must be divisible by 4

    override fun bytes(intArray: IntArray) : ByteArray {

        val blockCount = intArray.size / 4

        return ByteArray(blockCount * 3).init { set ->

            for (blockIndex in 0..<blockCount) {

                val intArrayIndex = blockIndex * 4

                val number0 = intArray[intArrayIndex].toUInt()
                val number1 = intArray[intArrayIndex + 1].toUInt()
                val number2 = intArray[intArrayIndex + 2].toUInt()
                val number3 = intArray[intArrayIndex + 3].toUInt()

                set(
                    (number0.shl(2) or number1.shr(4))
                        .toByte()
                )

                set(
                    (number1.shl(4) or number2.shr(2))
                        .toByte()
                )

                set(
                    (number2.shl(6) or number3)
                        .toByte()
                )
            }
        }
    }
}