package com.simplespace.android.lib.standard.inline

inline fun <A>A.but(block: () -> Unit) : A {
    block()
    return this
}

inline fun Boolean.alsoIf(block: () -> Unit) : Boolean {
    if (this) block()
    return this
}

inline fun <T>Boolean.letIf(block: () -> T) : T? = if (this) block() else null