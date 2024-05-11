package com.simplespace.android.lib.simple.compose.font

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.simplespace.android.lib.standard.math.font_size.FontSizeBundle

@Immutable
class Texts (
    private val fontSizes: FontSizeBundle,
) {

    @Composable
    fun title(text: String,) = Text(
        text = text,
        fontSize = fontSizes.title.sp,
    )

    @Composable
    fun font(text: String,) = Text(
        text = text,
        fontSize = fontSizes.font.sp,
    )

    @Composable
    fun h1(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h1.sp,
    )

    @Composable
    fun h2(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h2.sp,
    )

    @Composable
    fun h3(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h3.sp,
    )

    @Composable
    fun h4(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h4.sp,
    )

    @Composable
    fun h5(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h5.sp,
    )

    @Composable
    fun h6(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h6.sp,
    )

    @Composable
    fun h7(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h7.sp,
    )

    @Composable
    fun h8(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h8.sp,
    )

    @Composable
    fun h9(text: String,) = Text(
        text = text,
        fontSize = fontSizes.h9.sp,
    )
}