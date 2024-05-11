package com.simplespace.android.lib.standard.chain.reader

interface BasicStreamReader : BasicChainReader<ByteArray> {
    fun close()
}