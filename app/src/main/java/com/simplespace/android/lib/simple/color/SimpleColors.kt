package com.simplespace.android.lib.simple.color

import androidx.compose.ui.graphics.Color
import com.simplespace.android.lib.simple.color.SimpleColor.BLACK
import com.simplespace.android.lib.simple.color.SimpleColor.BLUE_TEXT
import com.simplespace.android.lib.simple.color.SimpleColor.DEEP_GREEN
import com.simplespace.android.lib.simple.color.SimpleColor.DEEP_ORANGE
import com.simplespace.android.lib.simple.color.SimpleColor.DEEP_RED
import com.simplespace.android.lib.simple.color.SimpleColor.ERROR_RED
import com.simplespace.android.lib.simple.color.SimpleColor.GRAY
import com.simplespace.android.lib.simple.color.SimpleColor.GREEN_TEXT
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_BLACK
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_GRAY
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_GREEN
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_GREEN_TEXT
import com.simplespace.android.lib.simple.color.SimpleColor.LIGHT_RED
import com.simplespace.android.lib.simple.color.SimpleColor.MELLOW_ORANGE
import com.simplespace.android.lib.simple.color.SimpleColor.NAVY
import com.simplespace.android.lib.simple.color.SimpleColor.WHITE

object SimpleColors {

    fun simpleColor(color: SimpleColor) : Color = elements[color]!!

    private val elements = mapOf(
        GRAY to Element.gray,
        BLACK to Element.black,
        WHITE to Element.white,
        ERROR_RED to Element.errorRed,
        NAVY to Element.navy,
        DEEP_RED to Element.deepRed,
        DEEP_GREEN to Element.deepGreen,
        LIGHT_GREEN to Element.lightGreen,
        DEEP_ORANGE to Element.deepOrange,
        LIGHT_GRAY to Element.lightGray,
        BLUE_TEXT to Element.blueText,
        LIGHT_RED to Element.lightRed,
        GREEN_TEXT to Element.greenText,
        LIGHT_GREEN_TEXT to Element.lightGreenText,
        MELLOW_ORANGE to Element.mellowOrange,
        LIGHT_BLACK to Element.lightBlack,
    )

    private object Element {

        val gray = Color(0xFFBBBBBB)

        val black = Color.Black

        val errorRed = Color(0xFFD80808)

        val white = Color.White
        val navy = Color(0xFF3311CC)
        val deepRed = Color(0xFFCC2222)
        val deepGreen = Color(0xFF00BB00)
        val lightGreen = Color(0xFF00CC77)
        val deepOrange = Color(0xFFCC5500)

        val lightGray = Color(0xFFD0D0D0)
        val blueText = Color(0xFF2266CC)
        val lightRed = Color(0xFFCC4444)
        val greenText = Color(0xFF22BB22)
        val lightGreenText = Color(0xFF22EE77)
        val mellowOrange = Color(0xFFFFAA00)

        val lightBlack = Color(0xFF202020)
    }
}