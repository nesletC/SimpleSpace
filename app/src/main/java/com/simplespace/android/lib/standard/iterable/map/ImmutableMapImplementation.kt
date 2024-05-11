package com.simplespace.android.lib.standard.iterable.map

class ImmutableMapImplementation<K, V>(
    override val asMap: Map<K, V>
) : ImmutableMap<K, V>, AbstractMap<K, V>(asMap)