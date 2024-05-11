package com.simplespace.android.lib.standard.iterable.list

abstract class AbstractList<T> (
    private val list: List<T>
) : List<T> {
    override val size: Int
        get() = list.size

    override fun get(index: Int): T = list[index]

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): Iterator<T> = list.iterator()

    override fun listIterator(): ListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = list.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = list.subList(fromIndex, toIndex)

    override fun lastIndexOf(element: T): Int = list.lastIndexOf(element)

    override fun indexOf(element: T): Int = list.indexOf(element)

    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)

    override fun contains(element: T): Boolean = list.contains(element)
}