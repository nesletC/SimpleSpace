package com.simplespace.android.lib.standard.timer

import android.util.Log
import com.simplespace.android.lib.standard.comparison.Findable
import com.simplespace.android.lib.standard.flow.DynamicFlow
import com.simplespace.android.lib.standard.inline.alsoIf
import com.simplespace.android.lib.standard.iterable.fixedIndicesList.IndexMap
import com.simplespace.android.lib.standard.time.Time

abstract class BasicSession<T: Findable<T>> {

    var newestSession : Int? = null
    private set

    fun getUpdates(updateInterval: Long) = DynamicFlow (
        updateInterval = updateInterval,
        getLatestUpdateTime = { _latestUpdate },
        getLatest = ::current
    )

    fun getMeta(index: Int) = dataMap[index]

    fun getLatest() = newestSession?.let{
        getMeta(it)
    }


    // returns false if a data session with the same data already exists

    fun start(data: T, maxTimeoutInMilliSeconds: Long) : Int? {

        dataCopy.forEach {

            if (it.value.equalsOther(data)) {

                resetTimeout(it.key)

                return it.key
            }
        }

        val sessionIndex = dataMap.add(data)

        newestSession = sessionIndex

        sessionsUpdated()

        sessionTimer.schedule(
            sessionIndex,
            maxTimeoutInMilliSeconds,
        ) {
            onSessionTimeout(sessionIndex)
        }

        return null
    }

    fun resetTimeout(index: Int) {
        sessionTimer.reset(index)
    }

    fun stop(index: Int) : Boolean = dataMap.remove(index).alsoIf {

        sessionsUpdated()

        sessionTimer.cancel(index)

        if (index == newestSession)
            newestSession = if (dataCopy.isEmpty()) null else dataCopy.keys.last()

        if (!isGarbageCollectionScheduled) {

            isGarbageCollectionScheduled = true

            garbageTimer.schedule(0, 10000L) {

                System.gc()

                isGarbageCollectionScheduled = false
            }
        }
    }

    fun clear() {
        dataMap.clear()
        sessionTimer.stop()
    }

    private fun current() : Map<Int, T> = dataCopy

    private fun onSessionTimeout(index: Int) {

        stop(index)
    }

    private fun sessionsUpdated() {
        _latestUpdate = Time.current()
        dataCopy = dataMap.copy
    }

    private var _latestUpdate = Time.current()

    private val dataMap = IndexMap<T>()

    private var dataCopy : Map<Int, T> = dataMap.copy

    private var isGarbageCollectionScheduled = false

    private val sessionTimer = BasicTimer(true)

    private val garbageTimer = BasicTimer(false)
}