package com.simplespace.android.lib.standard.composable.container

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.standard.composable.modifier.fillMaxHeight
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.math.ui_size.UISizeCalculator

@Composable
fun DynamicSideBarFrame(
    sideBarVisible: Boolean,
    maxWidthCalculations: UISizeCalculator,
    sideBarWidth: Dp,
    contentMinWidth: Dp,
    enter: EnterTransition = EnterTransition.None,
    exit : ExitTransition = ExitTransition.None,
    sideBarContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {

    Row {

        AnimatedVisibility(
            modifier = fillMaxHeight.width(sideBarWidth),
            visible = sideBarVisible,
            enter = enter,
            exit = exit,
        ) {
            sideBarContent()
        }

        val totalWidth = sideBarWidth + contentMinWidth
        
        if (totalWidth < maxWidthCalculations.maxDP)
            Box(
                Modifier.width(maxWidthCalculations.maxDP - contentMinWidth)
            ) {
                content()
            }
        else
            AnimatedVisibility(

                modifier = fillMaxWidth,
                visible = !sideBarVisible,
                enter = enter,
                exit = exit,
            ) {
                content()
            }
    }
}