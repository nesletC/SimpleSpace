package com.simplespace.android.lib.standard.composable.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerW(width: Dp) =
    Spacer(modifier = Modifier.width(width))

@Composable
fun SpacerH(height: Dp) =
    Spacer(modifier = Modifier.height(height))

@Composable
fun SpacerS(size: Dp) =
    Spacer(modifier = Modifier.size(size))
