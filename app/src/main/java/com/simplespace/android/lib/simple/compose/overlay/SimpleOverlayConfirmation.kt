package com.simplespace.android.lib.simple.compose.overlay

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplespace.android.lib.simple.color.SimpleColor
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.button.SimpleIconTextButton
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.position.Position

@Composable
fun AppScreenScope.SimpleOverlayConfirmation(
    visible: Boolean,
    message: String,
    label: String,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
) =
    SimpleOverlayConfirmation(
        visible = visible,
        label = label,
        escape = escape,
        onAbort = onAbort,
        onConfirm = onConfirm,
        message = {
            texts.h3(text = message)
        }
    )

@Composable
fun AppScreenScope.SimpleOverlayConfirmation(
    visible: Boolean,
    label: String,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
    message: @Composable () -> Unit,
) =
    SimpleOverlay(
        visible = visible,
        orientation = when (screenMode) {
            Position.ScreenMode.PORTRAIT -> Position.Orientation.VERTICAL
            Position.ScreenMode.LANDSCAPE -> Position.Orientation.HORIZONTAL
        },
        escape = {
            escape()
            onAbort()
        },
    ) {
        Column {

            SpacerH(10.dp)

            message()

            SpacerH(10.dp)

            mediumButtons.IconTextButton(icon = Icons.Default.Check, text = label) {
                escape()
                onConfirm()
            }

            SpacerH(10.dp)
        }
    }


@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    message: String,
    label: String,
    dangerous: Boolean = false,
    orientation: Position.Orientation = Position.Orientation.VERTICAL,
    borderWidth: Dp = 1.dp,
    iconSize: Dp = 30.dp,
    buttonPadding: Dp = 10.dp,
    fontSize: TextUnit = 14.sp,
    theme: SimpleColorTheme,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
) =
    SimpleOverlayConfirmation(
        visible = visible,
        message = message,
        label = label,
        dangerous = dangerous,
        orientation = orientation,
        borderWidth = borderWidth,
        iconSize = iconSize,
        buttonPadding = buttonPadding,
        fontSize = fontSize,
        surfaceBackground = theme.surfaceBackground,
        content = theme.content,
        background = theme.background,
        outlineColor = theme.outline,
        escape = escape,
        onAbort = onAbort,
        onConfirm = onConfirm,
    )

@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    label: String,
    dangerous: Boolean = false,
    orientation: Position.Orientation = Position.Orientation.VERTICAL,
    borderWidth: Dp = 1.dp,
    iconSize: Dp = 30.dp,
    buttonPadding: Dp = 10.dp,
    fontSize: TextUnit = 14.sp,
    theme: SimpleColorTheme,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
    message: @Composable () -> Unit,
) = SimpleOverlayConfirmation(
    visible = visible,
    label = label,
    orientation = orientation,
    dangerous = dangerous,
    borderWidth = borderWidth,
    iconSize = iconSize,
    buttonPadding = buttonPadding,
    fontSize = fontSize,
    surfaceBackground = theme.surfaceBackground,
    content = theme.content,
    background = theme.background,
    outlineColor = theme.outline,
    escape = escape,
    onAbort = onAbort,
    onConfirm = onConfirm,
    message = message,
)

@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    message: String,
    label: String,
    orientation: Position.Orientation,
    dangerous: Boolean = false,
    borderWidth: Dp = 1.dp,
    iconSize: Dp = 30.dp,
    buttonPadding: Dp = 10.dp,
    fontSize: TextUnit = 14.sp,
    surfaceBackground: Color = Color.Black,
    content: Color = Color.Black,
    background: Color = Color.White,
    outlineColor: Color = Color.Black,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
) =
    SimpleOverlayConfirmation(
        visible = visible,
        label = label,
        orientation = orientation,
        dangerous = dangerous,
        borderWidth = borderWidth,
        iconSize = iconSize,
        buttonPadding = buttonPadding,
        fontSize = fontSize,
        surfaceBackground = surfaceBackground,
        content = content,
        background = background,
        outlineColor = outlineColor,
        escape = escape,
        onAbort = onAbort,
        onConfirm = onConfirm
    ) {

        Text(text = message, fontSize = fontSize)

    }

@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    label: String,
    orientation: Position.Orientation,
    dangerous: Boolean = false,
    borderWidth: Dp = 1.dp,
    iconSize: Dp = 30.dp,
    buttonPadding: Dp = 10.dp,
    fontSize: TextUnit = 14.sp,
    surfaceBackground: Color = Color.Black,
    content: Color = Color.Black,
    background: Color = Color.White,
    outlineColor: Color = Color.Black,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    onConfirm: () -> Unit,
    message: @Composable () -> Unit,
) =
    SimpleOverlayConfirmation(
        visible = visible,
        orientation = orientation,
        surfaceBackground = surfaceBackground,
        escape = escape,
        onAbort = onAbort,
        message = message,
        confirmationButton = {

            SimpleIconTextButton(
                borderWidth = borderWidth,
                icon = Icons.Default.Check,
                contentDescription = "Confirm",
                iconSize = iconSize,
                fontSize = fontSize,
                text = label,
                padding = buttonPadding,
                backgroundColor = background,
                contentColor = if (dangerous)
                    SimpleColors.simpleColor(SimpleColor.ERROR_RED)
                else content,
                outlineColor = outlineColor,
                onClick = {
                    escape()
                    onConfirm()
                },
            )
        }
    )

@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    message: String,
    orientation: Position.Orientation,
    fontSize: TextUnit = 14.sp,
    surfaceBackground: Color = Color.Black,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    confirmationButton: @Composable () -> Unit,
) =
    SimpleOverlayConfirmation(
        visible = visible,
        orientation = orientation,
        surfaceBackground = surfaceBackground,
        escape = escape,
        onAbort = onAbort,
        message = {

            Text(text = message, fontSize = fontSize)

        },
        confirmationButton = confirmationButton
    )

@Composable
fun SimpleOverlayConfirmation(
    visible: Boolean,
    orientation: Position.Orientation,
    surfaceBackground: Color = Color.Black,
    escape: () -> Unit,
    onAbort: () -> Unit = {},
    message: @Composable () -> Unit,
    confirmationButton: @Composable () -> Unit,
) =
    SimpleOverlay(
        visible = visible,
        background = surfaceBackground,
        orientation = orientation,
        escape = {
            onAbort()
            escape()
        }
    ) {

        Column {

            SpacerH(height = 10.dp)

            message()

            SpacerH(height = 10.dp)

            confirmationButton()

            SpacerH(height = 10.dp)
        }
    }