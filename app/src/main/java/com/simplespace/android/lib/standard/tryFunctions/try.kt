package com.simplespace.android.lib.standard.tryFunctions

inline fun tryUnit(action: () -> Unit) = try{
    action()
} catch(_: Exception) { }

inline fun tryBoolean(action: () -> Boolean) : Boolean =
    try{
        action()
    } catch(_: Exception) {
        false
    }

inline fun tryOrFalse(action: () -> Unit) : Boolean =
    try {
        action()
        true
    } catch (_: Exception) {
        false
    }

inline fun <T>tryNull(action: () -> T?) : T? =
    try {
        action()
    } catch(_: Exception) {
        null
    }

inline fun <T>tryCatch(action: () -> T, catch: (Exception) -> T, ) : T =
    try {
        action()
    } catch(exception: Exception) {
        catch(exception)
    }

inline fun <T>tryElse(action: () -> Unit, catch: (Exception) -> T, elseBlock: () -> T) : T {
    try {
        action()
    } catch(exception: Exception) {
        return catch(exception)
    }
    return elseBlock()
}