package com.simplespace.android.lib.standard.random

inline fun <T>uniqueRandom(blackList: List<T>, getRandom: () -> T) : T {

    while(true) {

        val result = getRandom()

        if (!blackList.contains(result))
            return result
    }
}

inline fun <T>uniqueRandom(isUnique: (T) -> Boolean, getRandom: () -> T) : T {

    var result = getRandom()

    while(!isUnique(result)) {

        result = getRandom()
    }

    return result
}