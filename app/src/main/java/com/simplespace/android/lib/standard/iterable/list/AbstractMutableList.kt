package com.simplespace.android.lib.standard.iterable.list

class AbstractMutableList<T>(
    private val list: MutableList<T>
) : MutableList<T>, AbstractList<T>(list) {

    override fun add(element: T): Boolean = list.add(element)

    override fun add(index: Int, element: T) = list.add(index, element)

    override fun addAll(index: Int, elements: Collection<T>): Boolean = list.addAll(index, elements)

    override fun addAll(elements: Collection<T>): Boolean = list.addAll(elements)

    override fun clear() = list.clear()

    override fun iterator(): MutableIterator<T> = list.iterator()

    override fun listIterator(): MutableListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = list.listIterator(index)

    override fun removeAt(index: Int): T = list.removeAt(index)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = list.subList(fromIndex, toIndex)

    override fun set(index: Int, element: T): T = list.set(index, element)

    override fun retainAll(elements: Collection<T>): Boolean = list.retainAll(elements)

    override fun removeAll(elements: Collection<T>): Boolean = list.removeAll(elements)

    override fun remove(element: T): Boolean = list.remove(element)
}