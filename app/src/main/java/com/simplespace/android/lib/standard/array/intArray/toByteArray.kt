package com.simplespace.android.lib.standard.array.intArray

import com.simplespace.android.lib.standard.array.init
import java.nio.ByteBuffer

fun IntArray.toByteArray() = ByteArray(size).init {  set ->

    forEach{

        set(it.toUByte().toByte())
    }
}

fun IntArray.encodeToByteArray() : ByteArray {

    val result = ByteBuffer.allocate(size * 4)

    forEach{

        result.putInt(it)
    }

    return result.array()
}