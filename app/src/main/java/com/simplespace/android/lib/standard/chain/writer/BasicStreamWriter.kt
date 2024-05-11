package com.simplespace.android.lib.standard.chain.writer

interface BasicStreamWriter : BasicChainWriter<ByteArray> {

    fun close()
}