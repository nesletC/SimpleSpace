package com.simplespace.android.lib.standard.array.byteArray


fun ByteArray.set(uByte: UByte, offset: Int = 0) {
    set(offset, uByte.toByte())
}


fun ByteArray.set(short: Short, offset: Int = 0) {

    set( offset, short.toInt().shr(8 ).toByte() )

    set( offset + 1, short.toByte() )
}

fun ByteArray.set(uShort: UShort, offset: Int = 0) = set(uShort.toShort(), offset)


fun ByteArray.set(int: Int, offset: Int = 0) {

    for (i in 0..2) {
        set( offset + i, int.shr(( 3 - i ) * 8 ).toByte() )
    }

    set( offset + 3, int.toByte() )
}

fun ByteArray.set(uInt: UInt, offset: Int = 0) = set(uInt.toInt(), offset)


fun ByteArray.set(long: Long, offset: Int = 0) {

    for (i in 0..6) {

        set( offset + i, long.shr(( 7 - i ) * 8 ).toByte() )
    }

    set( offset + 7, long.toByte() )
}

fun ByteArray.set(uLong: ULong, offset: Int = 0) = set(uLong.toLong(), offset)