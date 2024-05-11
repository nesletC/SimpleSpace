package com.simplespace.android.lib.standard.iterable.fixedIndicesList


class IndexMap<T>(vararg elements: Pair<Int, T>) {

    private val _map = mutableMapOf(*elements)

    val map: Map<Int, T> = _map

    val copy get() = _map.toMutableMap()

    fun add(element: T): Int {
        val index =
            if (deletedIndices.size != 0)
                deletedIndices.removeLast()
            else
                _map.size

        _map[index] = element

        return index
    }

    operator fun get(index: Int) = _map[index]

    fun remove(index: Int) : Boolean =
        if (_map.remove(index) == null)
            false
        else {
            deletedIndices.add(index)
            true
        }

    fun clear() {
        _map.clear()
    }

    private val deletedIndices = mutableListOf<Int>()
}