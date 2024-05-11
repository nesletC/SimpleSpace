package com.simplespace.android.lib.standard.iterable.list

inline fun <T>list(perform: MutableList<T>.() -> Unit) : MutableList<T> =
    mutableListOf<T>().apply(perform)

inline fun <T>listIt(perform: (MutableList<T>) -> Unit) : MutableList<T> =
    list(perform)

inline fun <T>listOfSize(size: Int, forEach: (Int) -> T) : MutableList<T> =
    list{
        for (i in 0..<size)
            add(forEach(i))
    }

fun <T>immutableListOf(vararg elements: T) = listOf(*elements).toImmutableList()