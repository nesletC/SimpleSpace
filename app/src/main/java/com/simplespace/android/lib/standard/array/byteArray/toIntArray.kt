package com.simplespace.android.lib.standard.array.byteArray

import com.simplespace.android.lib.standard.array.init

fun ByteArray.toIntArray() : IntArray = IntArray(size).init{  set ->

    forEach{

        set(it.toUByte().toInt())
    }
}