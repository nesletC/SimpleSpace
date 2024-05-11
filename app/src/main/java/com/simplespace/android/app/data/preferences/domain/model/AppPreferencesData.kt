package com.simplespace.android.app.data.preferences.domain.model

import androidx.compose.runtime.Immutable
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.lib.simple.color.mode.SimpleColorModes
import com.simplespace.android.lib.simple.color.theme.SimpleColorThemes
import com.simplespace.android.lib.simple.style.SimpleDesignStyles

@Immutable
data class AppPreferencesData(
    val nightMode: Boolean = true,
    val nightModeAuto: Boolean = true,
    val textSize: Int = 14,
    val colorMode: Int = 0,
    val smoothMode: Boolean = false,
    val designStyle: Int = 0,
    val language: Int = 0,
    val showTutorial: Boolean = true,
) {

    val color get() =
        SimpleColors.simpleColor(
            SimpleColorModes.simpleColor(SimpleColorModes.entries[colorMode], nightMode)
        )

    fun actualNightMode(systemNightMode: Boolean) =
        if (nightModeAuto) systemNightMode else nightMode

    fun theme(actualNightMode: Boolean) = SimpleColorThemes.get(
        globalColorMode = SimpleColorModes.entries[colorMode],
        style = SimpleDesignStyles.entries[designStyle],
        nightMode = actualNightMode,
        lightMode = smoothMode,
    )
}