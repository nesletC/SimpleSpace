package com.simplespace.android.lib.simple.compose.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun AnimatedIcon(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    iconSize: Dp,
    foregroundTint: Color
) {

    Row (modifier){

        AnimatedVisibility(visible = visibility) {
            Icon(
                modifier = Modifier.requiredSize(iconSize),
                imageVector = Icons.Default.Circle,
                contentDescription = "A circle, part of a loading animation",
                tint = foregroundTint,
            )
        }
    }
}