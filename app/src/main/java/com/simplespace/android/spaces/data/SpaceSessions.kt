package com.simplespace.android.spaces.data

import com.simplespace.android.lib.standard.timer.BasicSession
import com.simplespace.android.spaces.model.SpaceMeta

object SpaceSessions : BasicSession<SpaceMeta>() {

    fun start(spaceMeta: SpaceMeta) =
        start(
            data = spaceMeta,
            maxTimeoutInMilliSeconds = spaceMeta.maxTimeoutInMinutes * 10000L
        )
}