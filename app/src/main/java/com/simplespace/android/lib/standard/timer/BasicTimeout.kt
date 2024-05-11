package com.simplespace.android.lib.standard.timer

import java.util.TimerTask

data class BasicTimeout(
    val task: TimerTask,
    val callback: () -> Unit,
    val delayInMilliseconds : Long,
    var start : Long = 0,
)
