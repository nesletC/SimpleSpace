package com.simplespace.android.lib.simple.color.mode

import com.simplespace.android.lib.simple.color.SimpleColor
import kotlin.enums.EnumEntries


object SimpleColorModes {

    val entries : EnumEntries<SimpleColorMode> = SimpleColorMode.entries

    fun simpleColor(mode: SimpleColorMode, nightMode: Boolean) : SimpleColor =
        modes[mode]!!.run {
            if (nightMode) night else day
        }

    private data class ColorKeyByNightMode(
        val day: SimpleColor,
        val night: SimpleColor
    )

    private val modes = mapOf(
        SimpleColorMode.BLUE
                to ColorKeyByNightMode(
            SimpleColor.NAVY, SimpleColor.BLUE_TEXT
        ),
        SimpleColorMode.GREEN
                to ColorKeyByNightMode(
            SimpleColor.DEEP_GREEN, SimpleColor.GREEN_TEXT
        ),
        SimpleColorMode.LIGHT_GREEN
                to ColorKeyByNightMode(
            SimpleColor.LIGHT_GREEN, SimpleColor.LIGHT_GREEN_TEXT
        ),
        SimpleColorMode.RED
                to ColorKeyByNightMode(
            SimpleColor.DEEP_RED, SimpleColor.LIGHT_RED
        ),
        SimpleColorMode.ORANGE
                to ColorKeyByNightMode(
            SimpleColor.DEEP_ORANGE, SimpleColor.MELLOW_ORANGE
        )
    )
}