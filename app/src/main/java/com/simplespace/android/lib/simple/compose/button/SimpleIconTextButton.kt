package com.simplespace.android.lib.simple.compose.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.icon.SimpleIcon
import com.simplespace.android.lib.standard.composable.spacer.SpacerW

@Composable
fun SimpleIconTextButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    icon: ImageVector,
    contentDescription: String? = null,
    iconSize: Dp,
    fontSize: TextUnit,
    text: String,
    padding: Dp,
    theme: SimpleColorTheme,
    onClick: () -> Unit,
) =
    SimpleIconTextButton(
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        fontSize = fontSize,
        text = text,
        borderWidth = borderWidth,
        padding = padding,
        iconSize = iconSize,
        contentColor = theme.content,
        backgroundColor = theme.background,
        outlineColor = theme.outline,
        onClick = onClick
    )

@Composable
fun SimpleIconTextButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    icon: ImageVector,
    contentDescription: String? = null,
    iconSize: Dp,
    fontSize: TextUnit,
    text: String,
    padding: Dp,
    contentColor: Color,
    backgroundColor: Color,
    outlineColor: Color,
    onClick: () -> Unit,
) = SimpleButton(
    modifier = modifier,
    borderWidth = borderWidth,
    contentColor = contentColor,
    backgroundColor = backgroundColor,
    outlineColor = outlineColor,
    paddingHorizontal = padding,
    paddingVertical = padding,
    onClick = onClick
) {

    SimpleIcon(
        icon = icon,
        contentDescription = contentDescription,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        size = iconSize
    )

    SpacerW(width = 8.dp)

    Text(text = text, fontSize = fontSize)
}