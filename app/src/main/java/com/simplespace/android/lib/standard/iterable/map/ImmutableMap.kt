package com.simplespace.android.lib.standard.iterable.map

import androidx.compose.runtime.Immutable

@Immutable
interface ImmutableMap<K, V>: Map<K, V> {
    val asMap: Map<K, V>
}