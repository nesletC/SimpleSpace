package com.simplespace.android.lib.standard.iterable.map

import com.simplespace.android.lib.standard.iterable.list.list

//      BASIC

fun <K, V>Map<K, V>.toImmutableMap() : ImmutableMap<K, V> = ImmutableMapImplementation(this)

fun <K, V>Map<K, V>.inverted() = mapMap{
    value to key
}

fun <K, V> Map <K, V>.getAll(keys: List<K>) : MutableList<V> =
    list {
        keys.forEach {
            add(get(it)!!)
        }
    }

fun <K, V> MutableMap <K, V>.updated (with : Map<K, V>) : MutableMap<K, V> = also {
    putAll(with)
}

fun <K, V> MutableMap <K, V>.removeAll(keys: List<K>) {
    keys.forEach {
        remove(it)
    }
}

fun <K, V> Map <K, V>.sub(keys: List<K>) : MutableMap<K, V> =
    mapOfIt { map ->
        keys.forEach {
            map[it] = get(it)!!
        }
    }


//      ITERATE

inline fun <K, V> Map <K, V>.runEach(action: Map.Entry<K, V>.() -> Unit) {
    forEach(action)
}

inline fun <K, V> Map <K, V>.forEachKey(action: (K) -> Unit) {
    keys.forEach(action)
}

inline fun <K, V> Map <K, V>.forEachValue(action: (V) -> Unit) {
    values.forEach(action)
}


inline fun <K, V> Map <K, V>.onEachKey(action: (K) -> Unit) : List<K> =
    keys.onEach(action).toList()

inline fun <K, V> Map <K, V>.onEachValue(action: (V) -> Unit) : List<V> =
    values.onEach(action).toList()


//      TO_LIST

fun <K, V>Map<K, V>.toList(keyList: List<K>) : MutableList<V> =
    list{
        keyList.forEach{
            add(get(it)!!)
        }
    }


//      TRANSFORM

inline fun <K, V, K2, V2>Map<K, V>.mapMap(
    transform: Map.Entry<K, V>.() -> Pair<K2, V2>
) : Map<K2, V2> =
    map(transform).toMap()

inline fun <K, V, K2, V2>Map<K, V>.mapMapIt(transformer: (params: Map.Entry<K, V>) -> Pair<K2, V2>) =
    mapMap(transformer)

inline fun <K, V, K2> Map <K, V>.mapKeysOnly(transform: K.() -> K2) : Map<K2, V> = mapMap{
    key.transform() to value
}

inline fun <K, V, K2> Map <K, V>.mapKeysOnlyIt(transform: (K) -> K2) : Map<K2, V> =
    mapKeysOnly(transform)

inline fun <K, V, V2> Map <K, V>.mapValuesOnly(transform: V.() -> V2) : Map<K, V2> =
    mapMap{
        key to value.transform()
    }

inline fun <K, V, V2> Map <K, V>.mapValuesOnlyIt(transform: (V) -> V2) =
    mapValuesOnly(transform)