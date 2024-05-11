package com.simplespace.android.lib.standard.array

inline fun <reified T>Array<T>.getAll(indices: List<Int>) : Array<T> =
    Array(indices.size) {
        this@getAll[it]
    }