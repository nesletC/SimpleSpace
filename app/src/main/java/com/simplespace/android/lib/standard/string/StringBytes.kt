package com.simplespace.android.lib.standard.string

import com.simplespace.android.lib.standard.number.BytesInts
import com.simplespace.android.lib.standard.number.StringInts

class StringBytes(
    private val bytesInts: BytesInts,
    private val stringInts: StringInts,
) {
    operator fun invoke(string: String) : ByteArray =
        bytesInts.bytes(stringInts(string))

    fun string(bytes: ByteArray) : String =
        stringInts.string(bytesInts(bytes))
}