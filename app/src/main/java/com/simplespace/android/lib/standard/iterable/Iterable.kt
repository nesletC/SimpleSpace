package com.simplespace.android.lib.standard.iterable

inline fun <T> Iterable<T>.runEach(action: T.() -> Unit) = forEach(action)