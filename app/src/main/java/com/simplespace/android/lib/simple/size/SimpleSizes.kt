package com.simplespace.android.lib.simple.size

interface SimpleSizes<T> {

    val small: T
    val medium: T
    val big: T

    operator fun get(size: SimpleSize) = when (size) {
        SimpleSize.LARGE -> small
        SimpleSize.MEDIUM -> medium
        SimpleSize.SMALL -> big
    }
}