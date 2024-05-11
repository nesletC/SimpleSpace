package com.simplespace.android.lib.standard.math.font_size

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object FontSizes {

    fun bundleOf(fontSize: Float, spToDpMultiplier: Float) : FontSizeBundle {

        val fontSizeDp = fontSize * spToDpMultiplier

        val lineHeight = fontSizeDp * 1.2f

        fun fontSize(multiplier: Float) = modifiedFontSize(
            fontSizeDp = fontSizeDp,
            fontSizeSp = fontSize,
            lineHeight = lineHeight,
            multiplier = multiplier,
        )

        return FontSizeBundle(
            title = fontSize(titleMultiplier),
            font = fontSize(
                fontSizeDp = fontSizeDp,
                fontSizeSp = fontSize,
                lineHeight = lineHeight
            ),
            h1 = fontSize(h1Multiplier),
            h2 = fontSize(h2Multiplier),
            h3 = fontSize(h3Multiplier),
            h4 = fontSize(h4Multiplier),
            h5 = fontSize(h5Multiplier),
            h6 = fontSize(h6Multiplier),
            h7 = fontSize(h7Multiplier),
            h8 = fontSize(h8Multiplier),
            h9 = fontSize(h9Multiplier),
        )
    }


    private fun modifiedFontSize(
        fontSizeDp: Float,
        fontSizeSp: Float,
        lineHeight: Float,
        multiplier: Float
    ) = fontSize(
        fontSizeDp = fontSizeDp * multiplier,
        fontSizeSp = fontSizeSp * multiplier,
        lineHeight = lineHeight * multiplier,
    )

    private fun fontSize(
        fontSizeDp: Float,
        fontSizeSp: Float,
        lineHeight: Float,
    ) = FontSize(
        dp = fontSizeDp.dp,
        sp = fontSizeSp.sp,
        lineHeight = lineHeight.dp
    )

    private const val titleMultiplier = 2f

    private const val h1Multiplier = 1.6f

    private const val h2Multiplier = 1.4f

    private const val h3Multiplier = 1.3f

    private const val h4Multiplier = 1.2f

    private const val h5Multiplier = 1.1f

    private const val h6Multiplier = 0.9f

    private const val h7Multiplier = 0.75f

    private const val h8Multiplier = 0.6f

    private const val h9Multiplier = 0.5f
}


