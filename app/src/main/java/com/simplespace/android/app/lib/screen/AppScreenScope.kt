package com.simplespace.android.app.lib.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.font.Texts
import com.simplespace.android.lib.simple.compose.uiSets.SimpleButtons
import com.simplespace.android.lib.simple.compose.uiSets.SimpleFixedSizeButtons
import com.simplespace.android.lib.simple.compose.uiSets.SimpleFixedSizeIcons
import com.simplespace.android.lib.simple.compose.uiSets.SimpleIcons
import com.simplespace.android.lib.standard.math.font_size.FontSizeBundle
import com.simplespace.android.lib.standard.math.ui_size.ScreenUnitConverter
import com.simplespace.android.lib.standard.math.ui_size.UISizeCalculator
import com.simplespace.android.lib.standard.position.Position

@Immutable
data class AppScreenScope(
    val nightMode: Boolean,
    val theme: SimpleColorTheme,
    val screenUnitConverter: ScreenUnitConverter,
    val fontSize: FontSizeBundle,
    val texts: Texts,
    val screenMode: Position.ScreenMode,
    val maxHeightCalculations: UISizeCalculator,
    val maxWidthCalculations: UISizeCalculator,
    val maxSizeCalculations: UISizeCalculator,
    val standardBorderWidth: Dp,
    val buttons: SimpleButtons,
    val icons: SimpleIcons,
    val bigButtons: SimpleFixedSizeButtons,
    val mediumButtons: SimpleFixedSizeButtons,
    val smallButtons: SimpleFixedSizeButtons,
    val bigIcons: SimpleFixedSizeIcons,
    val mediumIcons: SimpleFixedSizeIcons,
    val smallIcons: SimpleFixedSizeIcons,
)