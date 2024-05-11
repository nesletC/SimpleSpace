package com.simplespace.android.lib.standard.number

interface BytesInts {
    operator fun invoke(bytes: ByteArray) : IntArray
    fun bytes(intArray: IntArray) : ByteArray
}