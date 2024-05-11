package com.simplespace.android.lib.standard.iterable.map

inline fun <K, V>mapOf(perform: MutableMap<K, V>.() -> Unit) : MutableMap<K, V> =
    mutableMapOf<K, V>().apply(perform)

inline fun <K, V>mapOfIt(perform: (MutableMap<K, V>) -> Unit) : MutableMap<K, V> =
    mapOf(perform)

inline fun <K, V>mapOf(size: Int, forEach: (Int) -> Pair<K, V>) : MutableMap<K, V> =
    mapOf{
        for (i in 0..<size)
            forEach(i).let {
                put(it.first, it.second)
            }
    }

fun <K, V>immutableMapOf(vararg entries: Pair<K, V>) = mapOf(*entries).toImmutableMap()