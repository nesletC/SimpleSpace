package com.simplespace.android.lib.standard.iterable.map

class AbstractMutableMap<K, V>(
    private val map: MutableMap<K, V>
) : MutableMap<K, V>, AbstractMap<K, V>(map) {

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries
    override val keys: MutableSet<K>
        get() = map.keys
    override val values: MutableCollection<V>
        get() = map.values

    override fun clear() = map.clear()

    override fun remove(key: K): V? = map.remove(key)

    override fun putAll(from: Map<out K, V>) = map.putAll(from)

    override fun put(key: K, value: V): V? = map.put(key, value)
}