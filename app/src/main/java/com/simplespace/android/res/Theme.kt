package com.simplespace.android.res

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.simplespace.android.lib.simple.color.SimpleColor
import com.simplespace.android.lib.simple.color.SimpleColors.simpleColor

private val DefaultDarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val DefaultLightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val lightBlack = simpleColor(SimpleColor.LIGHT_BLACK)

private val DarkColorPalette = darkColors(
    primary = Color.LightGray,
    primaryVariant = Color.Gray,
    secondary = simpleColor(SimpleColor.LIGHT_RED),
    background = lightBlack
)


private val LightColorPalette = lightColors(
    primary = Color.Black,
    primaryVariant = lightBlack,
    secondary = simpleColor(SimpleColor.DEEP_RED),
    background = Color.White
)