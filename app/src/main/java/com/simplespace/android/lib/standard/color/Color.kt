package com.simplespace.android.lib.standard.color

import androidx.compose.ui.graphics.Color

fun Color.alpha(value: Int) = copy(alpha = value.toFloat() / 256)