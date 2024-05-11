package com.simplespace.android.lib.standard.composable.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simplespace.android.lib.standard.position.Position

@Composable
fun SplitFrames (
    modifier: Modifier,
    orientation: Position.Orientation,
    positionFirst: Position.Binary,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit,
) {

    when (orientation) {
        Position.Orientation.HORIZONTAL -> Row (modifier = modifier) {
            FinalArrangement(positionFirst, first, second)
        }
        Position.Orientation.VERTICAL -> Column (modifier = modifier) {
            FinalArrangement(positionFirst, first, second)
        }
    }
}

@Composable
private fun FinalArrangement(
    positionFirst: Position.Binary,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit
) = when(positionFirst) {

    Position.Binary.START -> {
        first()
        second()
    }
    Position.Binary.END -> {
        second()
        first()
    }
}