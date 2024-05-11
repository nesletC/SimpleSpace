package com.simplespace.android.lib.standard.string

inline fun string(action: CharArray.() -> Unit) : String =
    charArrayOf().apply(action).toString()

inline fun stringIt(action: (CharArray) -> Unit) : String = string(action)


inline fun stringOf(action: ((Char) -> Unit) -> Unit) = string {
    var position = 0
    action{
        set(position, it)
        position++
    }
}