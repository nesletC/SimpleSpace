package com.simplespace.android.lib.simple.compose.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.compose.loading.SimpleAnimatedLoadingIcon
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize

@Composable
fun AppScreenScope.SimpleOverlayLoading(
    visible: Boolean,
    borderWidth: Dp,
) {
    AnimatedVisibility(visible = visible) {
        Column(
            modifier = fillMaxSize
                .background(color = theme.surfaceBackground)
                .alpha(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SimpleAnimatedLoadingIcon(
                theme = theme,
                animationSize = maxSizeCalculations.percentage(25).dp,
                borderWidth = borderWidth
            )
        }
    }
}