package com.simplespace.android.lib.simple.compose.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme


@Composable
fun SimpleIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp,
    contentDescription: String? = null,
    theme: SimpleColorTheme,
) = SimpleIcon(
    icon = icon,
    modifier = modifier,
    size = size,
    backgroundColor = theme.background,
    contentColor = theme.content,
    contentDescription = contentDescription,
)

@Composable
fun SimpleIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Dp,
    contentDescription: String? = null,
    contentColor: Color,
    backgroundColor: Color? = null,
) {
    val endModifier = modifier.requiredSize(size)

    Icon(
        modifier = backgroundColor?.let{
            endModifier.background(it)
        }?:endModifier,
        imageVector = icon,
        contentDescription = contentDescription,
        tint = contentColor
    )
}

@Composable
fun SimpleIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Dp,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier.requiredSize(size),
        imageVector = icon,
        contentDescription = contentDescription,
    )
}