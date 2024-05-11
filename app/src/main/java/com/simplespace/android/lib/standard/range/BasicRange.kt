package com.simplespace.android.lib.standard.range

class BasicRange (
    offset: Int? = null
) {
    var start = offset?:0
    var end = start

    fun nextRange(length: Int) {
        var start = end
        var end = end + length
    }

    val value get() = start..<end
}