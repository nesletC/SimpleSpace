package com.simplespace.android.lib.standard.chain.writer

interface BasicChainWriter<T> {

    //returns the amount of elements that were actually written to the buffer

    fun write(array: T) : Int
}