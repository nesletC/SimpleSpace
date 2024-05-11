package com.simplespace.android.lib.simple.color.theme

import androidx.compose.ui.graphics.Color
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_BLACK
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_GRAY
import com.simplespace.android.lib.simple.color.SimpleColors.simpleColor
import com.simplespace.android.lib.simple.color.mode.SimpleColorMode
import com.simplespace.android.lib.simple.color.mode.SimpleColorModes
import com.simplespace.android.lib.simple.style.SimpleDesignStyle

object SimpleColorThemes {

    fun get(
        globalColorMode: SimpleColorMode,
        style: SimpleDesignStyle,
        nightMode: Boolean,
        lightMode: Boolean
    ) : SimpleColorTheme {

        val surfaceBackground =

            if (nightMode) {
                if (lightMode)
                    simpleColor(LIGHT_BLACK)//SimpleColors.elements[SimpleColor.LIGHT_BLACK]!!
                else Color.Black
            } else {
                if (lightMode) simpleColor(LIGHT_GRAY)
                else Color.White
            }

        val surfaceText =
            if (nightMode)
                if (lightMode)
                    simpleColor(LIGHT_GRAY)
                else
                    Color.White
            else
                if (lightMode)
                    simpleColor(LIGHT_BLACK)
                else
                    Color.Black

        val colorModeColor = simpleColor(
            SimpleColorModes.simpleColor(globalColorMode, nightMode)
        )


        return SimpleColorTheme(
            surfaceBackground = surfaceBackground,
            surfaceText = surfaceText,
            outline = when (style) {
                SimpleDesignStyle.BARE -> surfaceBackground
                else -> colorModeColor
            },
            background = when (style) {
                SimpleDesignStyle.FILLED -> colorModeColor
                else -> surfaceBackground
            },
            content = when (style) {
                SimpleDesignStyle.FILLED -> surfaceBackground
                else -> colorModeColor
            }
        )
    }
}