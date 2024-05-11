package com.simplespace.android.lib.standard.iterable.list

import androidx.compose.runtime.Immutable

@Immutable
interface ImmutableList<T> : List<T> {
    val asList: List<T>
}