package com.simplespace.android.lib.standard.number

import android.util.Log

class StringInts (
    private val charSet: String
) {

    operator fun invoke(string: String) : IntArray {

        val result = IntArray(string.length)

        for (index in result.indices) {

            result[index] = charSet.indexOf(string[index])
        }

        return result
    }

    fun string(intArray: IntArray) : String {

        var result = ""

        for (index in intArray) {

            result += charSet[index]
        }

        return result
    }
}