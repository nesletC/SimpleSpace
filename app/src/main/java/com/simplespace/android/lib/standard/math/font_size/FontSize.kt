package com.simplespace.android.lib.standard.math.font_size

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Immutable
class FontSize(
    val dp: Dp,
    val sp: TextUnit,
    val lineHeight: Dp
)