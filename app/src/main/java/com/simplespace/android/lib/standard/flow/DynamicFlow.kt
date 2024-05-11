package com.simplespace.android.lib.standard.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class DynamicFlow <T> (
    private val updateInterval: Long,
    private inline val getLatestUpdateTime: () -> Long,
    private inline val getLatest: () -> T,
){

    fun isLatest() = latestUpdate == getLatestUpdateTime()

    // gets the latest value and stops the flow from emitting the same update on the next interval

    fun current() : T {

        latestUpdate = getLatestUpdateTime()
        return getLatest()
    }

    // emits the latest update according to the interval set in the constructor

    val flow = flow<T?> {

        while(true) {

            emit(
                if (isLatest())
                    null
                else
                    getLatest()
            )
            delay(updateInterval)
        }
    }

    private var latestUpdate = 0L
}