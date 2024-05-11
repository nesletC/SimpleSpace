package com.simplespace.android.lib.standard.string

fun String.randomChars(length: Int) : String =
    stringOf { set ->

        for (index in 0..<length)
            set(random())
    }

fun String.nullIfBlank() = ifBlank { null }