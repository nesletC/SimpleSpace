package com.simplespace.android.lib.standard.chain.reader

interface BasicChainReaderSkippable<T> : BasicChainReader<T> {

    fun skip(length: Long)
}