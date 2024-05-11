package com.simplespace.android.lib.standard.number

import com.simplespace.android.lib.standard.array.copyOfLength
import com.simplespace.android.lib.standard.array.init


object BytesInts64PrefixedOverflow : BytesInts {

    // adds characters to the beginning of the string for the overflowing bytes

    override operator fun invoke(bytes: ByteArray) : IntArray {

        val size = bytes.size

        val overflow = size % 3

        val standardConversion =
            BytesInts64(bytes.copyOfRange(fromIndex = overflow, toIndex = size))

        return when (overflow) {

            0 -> standardConversion

            1 ->
                IntArray(2).init { set ->

                    val byte = bytes[0].toUByte().toInt()

                    set(
                        byte.shr(6)
                    )

                    set(
                        (byte and 63)
                    )
                }
                    .plus(standardConversion)

            else ->
                IntArray(3).init { set ->

                    val byte0 = bytes[0].toUByte().toInt()
                    val byte1 = bytes[1].toUByte().toInt()

                    set(
                        byte0.shr(4)
                    )

                    set(
                        ((byte0.shl(2) or byte1.shr(4)) and 63)
                    )
                    set(
                        (byte1 and 63)
                    )
                }
                    .plus(standardConversion)
        }
    }

    // converts the characters at the beginning back to bytes

    override fun bytes(intArray: IntArray) : ByteArray {

        val size = intArray.size

        val overflow = size % 4

        val standardConversion =
            BytesInts64.bytes(intArray.copyOfLength(overflow, length = size - overflow))

        return when (overflow) {

            0 -> standardConversion

            2 ->
                ByteArray(1).init { set ->

                    val int0 = intArray[0].toUInt()
                    val int1 = intArray[1].toUInt()

                    set(
                        (int0.shl(6) or int1)
                            .toByte()
                    )
                }
                    .plus(standardConversion)

            else ->                         // overflow should be 3
                ByteArray(2).init { set ->

                    val int0 = intArray[0].toUInt()
                    val int1 = intArray[1].toUInt()
                    val int2 = intArray[2].toUInt()

                    set(
                        (int0.shl(4) or int1.shr(4))
                            .toByte()
                    )

                    set(
                        (int1.shl(6) or int2)
                            .toByte()
                    )
                }
                    .plus(standardConversion)
        }
    }
}