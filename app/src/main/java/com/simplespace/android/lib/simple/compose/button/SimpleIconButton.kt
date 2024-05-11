package com.simplespace.android.lib.simple.compose.button

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    borderWidth: Dp,
    padding: Dp,
    size: Dp,
    theme: SimpleColorTheme,
    onClick: () -> Unit,
) =
    SimpleIconButton(
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        borderWidth = borderWidth,
        padding = padding,
        size = size,
        contentColor = theme.content,
        backgroundColor = theme.background,
        outlineColor = theme.outline,
        onClick = onClick
    )

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    borderWidth: Dp,
    padding: Dp,
    size: Dp,
    contentColor: Color,
    backgroundColor: Color,
    outlineColor: Color,
    onClick: () -> Unit,
) =
    SimpleButton(
        modifier = modifier,
        borderWidth = borderWidth,
        paddingHorizontal = padding,
        paddingVertical = padding,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        outlineColor = outlineColor,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.requiredSize(size),
        )
    }