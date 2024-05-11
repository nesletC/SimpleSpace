package com.simplespace.android.lib.simple.compose.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.icon.SimpleIcon
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.color.alpha
import com.simplespace.android.lib.standard.composable.modifier.fillMaxHeight
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.position.Position

@Composable
fun AppScreenScope.SimpleOverlay(
    visible: Boolean,
    escape: () -> Unit,
    orientation: Position.Orientation = when (screenMode) {
        Position.ScreenMode.PORTRAIT -> Position.Orientation.VERTICAL
        Position.ScreenMode.LANDSCAPE -> Position.Orientation.HORIZONTAL
    },
    content: @Composable () -> Unit,
) =
    SimpleOverlay(
        visible = visible,
        escape = escape,
        orientation = orientation,
        theme = theme,
        content = content,
    )

@Composable
fun SimpleOverlay(
    visible: Boolean,
    escape: () -> Unit,
    orientation: Position.Orientation = Position.Orientation.VERTICAL,
    theme: SimpleColorTheme,
    content: @Composable () -> Unit,
) =
    SimpleOverlay(
        visible = visible,
        escape = escape,
        orientation = orientation,
        background = theme.surfaceBackground,
        content = content,
    )

@Composable
fun SimpleOverlay (
    visible: Boolean,
    background: Color = Color.Black,
    orientation: Position.Orientation = Position.Orientation.VERTICAL,
    escape: () -> Unit,
    content: @Composable () -> Unit
) {

    val enterTransition = when (orientation) {
        Position.Orientation.HORIZONTAL -> slideInHorizontally { it }
        Position.Orientation.VERTICAL -> slideInVertically { it }
    }

    val exitTransition = when (orientation) {
        Position.Orientation.HORIZONTAL -> slideOutHorizontally { it }
        Position.Orientation.VERTICAL -> slideOutVertically { it }
    }

    AnimatedVisibility(
        visible = visible,
        modifier = fillMaxSize,
        enter = enterTransition,
        exit = exitTransition,
    ) {

        Box(
            modifier = fillMaxSize
                .clickable {}
                .background(
                    Color.Gray
                        .alpha(60)
                )
        ) {

            when(orientation) {
                Position.Orientation.HORIZONTAL -> {
                    Row(
                        modifier = fillMaxHeight
                            .align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Divider(
                            fillMaxHeight
                                .width(1.dp)
                                .background(Color.Gray)
                        )
                        Column(
                            modifier = fillMaxHeight
                                .clickable(onClick = escape),
                            verticalArrangement = Arrangement.Center
                        ) {
                            SpacerW(width = 10.dp)
                            EscapeButton()
                            SpacerW(width = 10.dp)
                        }
                        Column(
                            fillMaxHeight
                                .verticalScroll(rememberScrollState())
                                .background(color = background),
                            verticalArrangement = Arrangement.Center,
                        ) {

                            content()
                        }
                    }
                }
                Position.Orientation.VERTICAL -> {
                    Column(
                        modifier = fillMaxWidth
                            .align(Alignment.BottomCenter),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Divider(
                            fillMaxWidth
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Row(
                            fillMaxWidth
                                .clickable(onClick = escape),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            SpacerW(width = 10.dp)
                            EscapeButton()
                            SpacerW(width = 10.dp)
                        }
                        Column(
                            modifier = fillMaxWidth
                                .verticalScroll(rememberScrollState())
                                .background(color = background),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            SpacerH(height = 20.dp)
                            content()
                            SpacerH(height = 20.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EscapeButton() {

    SimpleIcon(icon = Icons.Default.Close, contentDescription = "Close", size = 50.dp)
}