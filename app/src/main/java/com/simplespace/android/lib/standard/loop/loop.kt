package com.simplespace.android.lib.standard.loop

inline fun loopN(times: Int, block: () -> Unit) {
    var index = 0
    while(index < times){
        block()
        index++
    }
}
