package com.simplespace.android.lib.simple.compose.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme

@Composable
fun SimpleDropDownIcon(
    modifier: Modifier = Modifier,
    isDropped: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
    theme: SimpleColorTheme,
) =
    SimpleDropDownIcon(
        modifier = modifier,
        isDropped = isDropped,
        size = size,
        padding = padding,
        contentColor = theme.content,
        backgroundColor = theme.background,
    )

@Composable
fun SimpleDropDownIcon(
    modifier: Modifier = Modifier,
    isDropped: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
    contentColor: Color,
    backgroundColor: Color,
) =
    SimpleToggleIcon(
        modifier = modifier,
        isSelected = isDropped,
        selectedIcon = Icons.Default.ArrowDropUp,
        unSelectedIcon = Icons.Default.ArrowDropDown,
        contentDescription = if (isDropped) "drop up" else "drop down",
        size = size,
        padding = padding,
        contentColor = contentColor,
        backgroundColor = backgroundColor
    )

@Composable
fun SimpleDropDownIcon(
    modifier: Modifier = Modifier,
    isDropped: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
) =
    SimpleToggleIcon(
        modifier = modifier,
        isSelected = isDropped,
        selectedIcon = Icons.Default.ArrowDropUp,
        unSelectedIcon = Icons.Default.ArrowDropDown,
        contentDescription = if (isDropped) "drop up" else "drop down",
        size = size,
        padding = padding,
    )