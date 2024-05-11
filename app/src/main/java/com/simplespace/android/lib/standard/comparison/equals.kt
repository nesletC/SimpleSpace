package com.simplespace.android.lib.standard.comparison

fun <T>T.equalsAny(vararg values: T) : Boolean {
    values.forEach {
        if (this == it)
            return true
    }
    return false
}

fun <T>T.equalsAll(vararg values: T) : Boolean {
    values.forEach{
        if (this != it)
            return false
    }
    return true
}