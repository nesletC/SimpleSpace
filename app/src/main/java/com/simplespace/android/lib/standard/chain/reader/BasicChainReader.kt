package com.simplespace.android.lib.standard.chain.reader

interface BasicChainReader<T> {

    // this will be updated as soon as the final bytes have been read from this reader

    val isReadable: Boolean


    // reads exactly the amount of chain elements that can be read into an array of that length

    fun read(length: Int) : T
}