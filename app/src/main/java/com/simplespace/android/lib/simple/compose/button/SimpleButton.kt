package com.simplespace.android.lib.simple.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    theme: SimpleColorTheme,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) = SimpleButton(
    modifier = modifier,
    borderWidth = borderWidth,
    contentColor = theme.content,
    backgroundColor = theme.background,
    outlineColor = theme.outline,
    paddingHorizontal = paddingHorizontal,
    paddingVertical = paddingVertical,
    onClick = onClick,
    content = content,
)

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    contentColor: Color,
    backgroundColor: Color,
    outlineColor: Color,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(borderWidth, outlineColor),
        contentPadding =
        PaddingValues(horizontal = paddingHorizontal, vertical = paddingVertical),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledContentColor = Color.Gray,
        ),
        onClick = onClick,
        content = content,
    )
}