package com.simplespace.android.lib.standard.number


fun Byte.toByteArray() = byteArrayOf(this)

fun UByte.toByteArray() = byteArrayOf(toByte())



fun Short.toByteArray() = byteArrayOf(
    toInt().shr(8).toByte(),
    toByte()
)

fun UShort.toByteArray() = toShort().toByteArray()

fun Int.toByteArray() = byteArrayOf(
    shr(24).toByte(),
    shr(16).toByte(),
    shr(8).toByte(),
    toByte()
)

fun UInt.toByteArray() = toInt().toByteArray()

fun Long.toByteArray() = byteArrayOf(
    shr(56).toByte(),
    shr(48).toByte(),
    shr(40).toByte(),
    shr(32).toByte(),
    shr(24).toByte(),
    shr(16).toByte(),
    shr(8).toByte(),
    toByte(),
)

fun ULong.toByteArray() = toLong().toByteArray()