
package com.simplespace.android.lib.standard.iterable.list

import com.simplespace.android.lib.standard.inline.but
import com.simplespace.android.lib.standard.iterable.map.mapOfIt
import com.simplespace.android.lib.standard.iterable.map.runEach

// important standard kotlin library functions:

//List<T>.map((T) -> R) : List<R>

//List<List<T>>.flatten() : List<T>

// List<Pair<K, V>>.toMap() : Map<K, V>

// List<K>.zip(values: List<V>) : List<Pair<K, V>>


//      BASIC

fun <T>List<T>.toImmutableList() : ImmutableList<T> = ImmutableListImplementation(this)

fun <T>MutableList<T>.addIfNew(element: T) {
    if (!contains(element)) add(element)
}

fun <T, R>List<T>.mapMutable(action: (T) -> R) = listIt{
    forEach{ entry ->
        it.add(action(entry))
    }
}

fun <K, V>List<K>.zipMap(values: List<V>) = zip(values).toMap() //returns Map<K, V>

fun <T>List<T>.getAll(indices: List<Int>) : MutableList<T> =
    listIt { list ->
        indices.forEach {
            list.add(get(it))
        }
    }

fun <T>List<T>.getAllExcept(indices: List<Int>) : MutableList<T> = toMutableList().apply{
    removeAll(indices)
}

fun <T>MutableList<T>.setAll(values: Map<Int, T>) {
    values.runEach {
        set(key, value)
    }
}

fun <T>MutableList<T>.removeAll(indices: List<Int>) {
    indices.forEach {
        removeAt(it)
    }
}


fun <T>MutableList<T>.apply(values: Map<Int, T>) = but {
    setAll(values)
}

fun <T>List<T>.sub(size: Int, offset: Int = 0) =
    subList(offset, offset + size)


//      ITERATE

inline fun <T>List<T>.forEachIndex(action: (Int) -> Unit) {
    for (i in indices) action(i)
}

inline fun <T>List<T>.runEachIndex(action: Int.() -> Unit) = forEachIndex(action)


//      INDICES_MAP

fun <K, V>List<Pair<K, V>>.getKeysIndices() : MutableMap<K, Int> =
    mapOfIt {
        forEachIndexed { index, pair ->
            it[pair.first] = index
        }
    }

fun <K, V>List<Pair<K, V>>.getValuesIndices() : MutableMap<V, Int> =
    mapOfIt {
        forEachIndexed { index, pair ->
            it[pair.second] = index
        }
    }

fun <T>List<T>.getIndicesMap() : MutableMap<T, Int> =
    mapOfIt {
        forEachIndexed { index, t ->
            it[t] = index
        }
    }


//      TRANSFORMATION

inline fun <S, T>List<S>.mapRun(invoker: S.() -> T) = map(invoker)