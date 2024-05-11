package com.simplespace.android.lib.simple.compose.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.standard.composable.modifier.showIf

@Composable
fun SimpleToggleIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector,
    contentDescription: String? = null,
    size: Dp,
    padding: Dp,
    theme: SimpleColorTheme,
) =
    SimpleToggleIcon(
        modifier = modifier,
        isSelected = isSelected,
        selectedIcon = selectedIcon,
        unSelectedIcon = unSelectedIcon,
        size = size,
        padding = padding,
        backgroundColor = theme.background,
        contentColor = theme.content,
        contentDescription = contentDescription,
    )

@Composable
fun SimpleToggleIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector,
    contentDescription: String? = null,
    size: Dp,
    padding: Dp = 0.dp,
    contentColor: Color,
    backgroundColor: Color? = null,
) {
    Box(modifier.padding(padding)){
        SimpleIcon(
            modifier = Modifier.showIf(isSelected),
            icon = selectedIcon,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            size = size
        )
        SimpleIcon(
            modifier = Modifier.showIf(!isSelected),
            icon = unSelectedIcon,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            contentDescription = contentDescription,
            size = size
        )
    }
}

@Composable
fun SimpleToggleIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector,
    contentDescription: String? = null,
    size: Dp,
    padding: Dp = 0.dp,
) =
    Box(modifier.padding(padding)){
        SimpleIcon(
            modifier = Modifier.showIf(isSelected),
            icon = selectedIcon,
            size = size
        )
        SimpleIcon(
            modifier = Modifier.showIf(!isSelected),
            icon = unSelectedIcon,
            contentDescription = contentDescription,
            size = size
        )
    }