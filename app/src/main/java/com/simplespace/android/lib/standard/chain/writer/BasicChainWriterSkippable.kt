package com.simplespace.android.lib.standard.chain.writer

interface BasicChainWriterSkippable<T> : BasicChainWriter<T> {

    fun skip(length: Int)
}