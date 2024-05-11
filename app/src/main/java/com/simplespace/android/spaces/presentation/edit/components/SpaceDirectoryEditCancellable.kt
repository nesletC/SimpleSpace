package com.simplespace.android.spaces.presentation.edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import com.simplespace.android.lib.simple.color.SimpleColor
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.res.Shapes
import com.simplespace.android.spaces.presentation.edit.SpaceDirectoryUpdateMeta


@Composable
fun AppScreenScope.SpaceDirectoryEditCancellable(
    moveOn: () -> Unit,
    updateData: SpaceDirectoryUpdateMeta,
    isLabelEditable: Boolean = true,
) {

    val actionColor =
        if (updateData.cancel.value)
            SimpleColors.simpleColor(SimpleColor.GREEN_TEXT)
        else
            SimpleColors.simpleColor(SimpleColor.ERROR_RED)

    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor =
            if (nightMode)
                Color.Black
            else
                actionColor,
            contentColor =
            if (nightMode)
                actionColor
            else
                Color.White,
        ),
        shape = Shapes.large,
        border = BorderStroke(standardBorderWidth, brush = Brush.radialGradient(
            colors = listOf(Color.Gray, actionColor), tileMode = TileMode.Repeated
        )),
        onClick = {
            updateData.cancel.value = !updateData.cancel.value
        }
    ) {
        Text(
            text = if(updateData.cancel.value)
                "Create " + updateData.label.value
            else
                "Delete " + updateData.label.value
        )
    }

    AnimatedVisibility(visible = !updateData.cancel.value) {
        SpaceDirectoryEdit(
            moveOn = moveOn,
            updateData = updateData,
            isLabelEditable = isLabelEditable,
        )
    }
}