package com.simplespace.android.lib.simple.session

import com.simplespace.android.lib.standard.timer.BasicTimer
import java.lang.System.gc

class SimpleSessions<T> {

    fun get(index: Int) = dataSets[index]

    fun save(data: T, maxTimeoutInMilliseconds: Long) : Int {

        dataSets.add(data)

        val index = dataSets.lastIndex

        sessionTimer.schedule(
            index,
            maxTimeoutInMilliseconds
        ) {
            delete(index)
        }

        return index
    }

    fun resetTimeout(index: Int) {
        sessionTimer.reset(index)
    }

    fun delete(index: Int){

        dataSets.removeAt(index)

        if (!isGarbageCollectionScheduled) {

            isGarbageCollectionScheduled = true

            garbageTimer.schedule(0, 10000L) {

                gc()

                isGarbageCollectionScheduled = false
            }
        }
    }

    fun clear() {
        dataSets.clear()
        sessionTimer.stop()
    }

    var isGarbageCollectionScheduled = false

    private val dataSets = mutableListOf<T>()

    private val sessionTimer = BasicTimer(true)

    private val garbageTimer = BasicTimer(false)
}