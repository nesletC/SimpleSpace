package com.simplespace.android.lib.standard.state

import androidx.compose.runtime.MutableState

inline fun <T> MutableState<T>.update(getNewState: (T) -> T) {
    value = getNewState(value)
}