package com.simplespace.android.app.lib.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.font.Texts
import com.simplespace.android.lib.simple.compose.uiSets.SimpleButtons
import com.simplespace.android.lib.simple.compose.uiSets.SimpleIcons
import com.simplespace.android.lib.standard.math.font_size.FontSizes
import com.simplespace.android.lib.standard.math.ui_size.ScreenUnitConversion
import com.simplespace.android.lib.standard.math.ui_size.ScreenUnitConverter
import com.simplespace.android.lib.standard.math.ui_size.UISizeCalculator
import com.simplespace.android.lib.standard.position.Position

@Composable
fun appScreenScope(
    preferences: AppPreferencesData,
    nightMode: Boolean,
    theme: SimpleColorTheme,
    maxWidth: Dp,
    maxHeight: Dp,
) : AppScreenScope {
    val screenUnitConverter = ScreenUnitConverter(LocalDensity.current)

    val fontSize = remember{
        FontSizes.bundleOf(
            preferences.textSize.toFloat(), screenUnitConverter.spToDP
        )
    }

    val screenMode =
        if (maxWidth > maxHeight)
            Position.ScreenMode.LANDSCAPE
        else
            Position.ScreenMode.PORTRAIT

    val maxHeightCalculations =
        UISizeCalculator(
            screenUnitConverter,
            screenUnitConverter.convert(maxHeight.value, ScreenUnitConversion.DP_TO_PX)
        )
    val maxWidthCalculations =
        UISizeCalculator(
            screenUnitConverter,
            screenUnitConverter.convert(maxWidth.value, ScreenUnitConversion.DP_TO_PX)
        )

    val standardBorderWidth = maxHeightCalculations
        .minPX(1)
        .minDP(0.2)
        .maxDP(1)
        .dp

    val buttons = SimpleButtons(
        theme,
        standardBorderWidth
    )

    val icons = SimpleIcons(
        theme
    )

    val bigIconSize = maxWidthCalculations
        .dp(fontSize.h1.lineHeight.value * 1.5)
        .px(100)
        .maxDP(60)
        .dp(45)
        .dp

    val mediumIconSize = maxWidthCalculations
        .dp(fontSize.h3.lineHeight.value)
        .px(60)
        .maxDP(40)
        .dp(30)
        .dp

    val smallIconSize = maxWidthCalculations
        .dp(fontSize.font.lineHeight.value)
        .px(30)
        .maxDP(35)
        .dp(22)
        .dp

    return AppScreenScope(
        nightMode = nightMode,
        theme = theme,
        screenUnitConverter = screenUnitConverter,
        fontSize = fontSize,
        texts = Texts(fontSize),
        screenMode = screenMode,
        maxHeightCalculations = maxHeightCalculations,
        maxWidthCalculations = maxWidthCalculations,
        maxSizeCalculations = when(screenMode) {
            Position.ScreenMode.PORTRAIT -> maxWidthCalculations
            Position.ScreenMode.LANDSCAPE -> maxHeightCalculations
        },
        standardBorderWidth = standardBorderWidth,
        buttons = buttons,
        icons = icons,
        bigButtons =
        buttons.fixedSize(
            size = bigIconSize,
            fontSize = fontSize.title.sp,
        ),
        mediumButtons =
        buttons.fixedSize(
            size = mediumIconSize,
            fontSize = fontSize.h4.sp,
        ),
        smallButtons =
        buttons.fixedSize(
            size = smallIconSize,
            fontSize = fontSize.font.sp,
        ),
        bigIcons = icons.fixedSize(bigIconSize),
        mediumIcons = icons.fixedSize(mediumIconSize),
        smallIcons = icons.fixedSize(smallIconSize),
    )
}