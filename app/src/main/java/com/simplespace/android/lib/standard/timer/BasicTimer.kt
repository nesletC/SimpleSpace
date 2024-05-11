package com.simplespace.android.lib.standard.timer

import com.simplespace.android.lib.standard.time.Time
import java.util.Timer
import java.util.TimerTask

// before starting the timer!!!
// the maxTimeout has to be set by changing the maxTimeoutInMilliSeconds property
// when the timer is started, then, after a delay of maxTimeoutInMilliSeconds
// 1. if the resetFunction was called in the mean time, a new timer is started
// 2. else callback is called

class BasicTimer (
    isDaemon: Boolean = false,
) {

    private val tasks = mutableMapOf<Int, BasicTimeout>()

    private val timer = Timer(isDaemon)

    fun schedule(index: Int, timeoutDelayInMilliseconds: Long, callback: () -> Unit) {

        val timeout = BasicTimeout(
            task = TimerAction{
                checkTimeout(index)
            },
            callback = callback,
            delayInMilliseconds = timeoutDelayInMilliseconds,
        )

        tasks[index] = timeout

        timer.schedule(timeout.task, timeout.delayInMilliseconds)

        timeout.start = Time.current()
    }

    fun reset(index: Int) {
        tasks[index]?.start = Time.current()
    }

    fun cancel(index: Int) {
        tasks[index]?.task?.cancel()
    }

    fun stop() {
        timer.cancel()
    }

    private fun checkTimeout(index: Int) {

        val timeout = tasks[index]!!

        val remainingTime =
            Time.current() - timeout.start - timeout.delayInMilliseconds
        if (
            remainingTime < 1000
        )
            timeout.callback
        else
            timer.schedule(timeout.task, remainingTime)
    }

    private class TimerAction(private val callback: () -> Unit) : TimerTask() {
        override fun run() {
            callback()
        }
    }
}
