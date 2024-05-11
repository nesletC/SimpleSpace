package com.simplespace.android.lib.simple.compose.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import kotlinx.coroutines.delay

@Composable
fun SimpleAnimatedLoadingIcon(
    theme: SimpleColorTheme,
    animationSize: Dp,
    borderWidth: Dp,
) =
    SimpleAnimatedLoadingIcon(
        foregroundTint = theme.content,
        backgroundTint = theme.background,
        borderTint = theme.outline,
        animationSize = animationSize,
        borderWidth = borderWidth
    )

@Composable
private fun SimpleAnimatedLoadingIcon(
    modifier: Modifier = Modifier,
    foregroundTint: Color,
    backgroundTint: Color,
    borderTint: Color,
    animationSize: Dp,
    borderWidth: Dp,
) {

    val iconSize = animationSize / 7

    @Composable
    fun spacer() = Spacer(modifier = Modifier.size(iconSize))

    Box (
        modifier = modifier
            .background(backgroundTint)
            .border(width = borderWidth, color = borderTint)
    ){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var iterations = 0

            var visibilityAnimationDot1 = false
            var visibilityAnimationDot2 = false
            var visibilityAnimationDot3 = false
            var visibilityAnimationDot4 = false
            var visibilityAnimationDot5 = false
            var visibilityAnimationDot6 = false
            var visibilityAnimationDot7 = false
            var visibilityAnimationDot8 = false
            var visibilityAnimationDot9 = false

            LaunchedEffect(key1 = iterations) {

                when (iterations) {
                    0 -> visibilityAnimationDot1 = true
                    1 -> {
                        visibilityAnimationDot2 = true
                        visibilityAnimationDot4 = true
                    }
                    2 -> {
                        visibilityAnimationDot3 = true
                        visibilityAnimationDot5 = true
                        visibilityAnimationDot7 = true
                    }
                    3 -> {

                        visibilityAnimationDot6 = true
                        visibilityAnimationDot8 = true
                    }
                    4 -> visibilityAnimationDot9 = true
                    5 -> visibilityAnimationDot1 = false
                    6 -> {
                        visibilityAnimationDot2 = false
                        visibilityAnimationDot4 = false
                    }
                    7 -> {
                        visibilityAnimationDot3 = false
                        visibilityAnimationDot5 = false
                        visibilityAnimationDot7 = false
                    }
                    8 -> {

                        visibilityAnimationDot6 = false
                        visibilityAnimationDot8 = false
                    }
                    9 -> {
                        visibilityAnimationDot9 = false
                        iterations = -1
                    }
                }

                iterations++

                delay(50)
            }

            @Composable
            fun icon(visible: Boolean) = AnimatedIcon(
                visibility = visible,
                iconSize = iconSize,
                foregroundTint = foregroundTint
            )

            spacer()
            Row {
                spacer()
                icon(visibilityAnimationDot1)
                spacer()
                icon(visibilityAnimationDot2)
                spacer()
                icon(visibilityAnimationDot3)
                spacer()
            }
            spacer()
            Row {
                spacer()
                icon(visibilityAnimationDot4)
                spacer()
                icon(visibilityAnimationDot5)
                spacer()
                icon(visibilityAnimationDot6)
                spacer()
            }
            spacer()
            Row{
                spacer()
                icon(visibilityAnimationDot7)
                spacer()
                icon(visibilityAnimationDot8)
                spacer()
                icon(visibilityAnimationDot9)
                spacer()
            }
            spacer()
        }
    }
}