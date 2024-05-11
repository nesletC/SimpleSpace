package com.simplespace.android.lib.standard.composable.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.showIf(condition: Boolean = false) : Modifier = if (condition) this else this.alpha(0f)