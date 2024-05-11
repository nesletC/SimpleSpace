package com.simplespace.android.lib.standard.array.byteArray


fun ByteArray.getUByte( position: Int = 0 ) = get(position).toUByte()


fun ByteArray.getShort( offset: Int = 0 ) = getUShort(offset).toShort()

fun ByteArray.getUShort( offset: Int = 0 ) =
    (
        getUByte( offset ).toUInt().shl(8) or
        getUByte( offset + 1 ).toUInt()
    )
        .toUShort()



fun ByteArray.getInt( offset: Int = 0 ) = getUInt(offset).toInt()

fun ByteArray.getUInt( offset: Int = 0 ) =

    getUByte( offset ).toUInt().shl(24) or
    getUByte( offset + 1 ).toUInt().shl(16) or
    getUByte( offset + 2 ).toUInt().shl(8) or
    getUByte( offset + 3 ).toUInt()


fun ByteArray.getLong( offset: Int = 0 ) = getULong(offset).toLong()

fun ByteArray.getULong( offset: Int = 0 ) =

    getUByte( offset ).toULong().shl(54) or
    getUByte(offset + 1 ).toULong().shl(48) or
    getUByte(offset + 2 ).toULong().shl(40) or
    getUByte(offset + 3 ).toULong().shl(32) or
    getUByte(offset + 4 ).toULong().shl(24) or
    getUByte(offset + 5 ).toULong().shl(16) or
    getUByte(offset + 6 ).toULong().shl(8) or
    getUByte(offset + 7 ).toULong()
