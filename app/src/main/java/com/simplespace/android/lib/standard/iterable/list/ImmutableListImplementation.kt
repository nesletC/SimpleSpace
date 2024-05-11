package com.simplespace.android.lib.standard.iterable.list

class ImmutableListImplementation<T> (
    override val asList: List<T>
) : ImmutableList<T>, AbstractList<T>(asList)