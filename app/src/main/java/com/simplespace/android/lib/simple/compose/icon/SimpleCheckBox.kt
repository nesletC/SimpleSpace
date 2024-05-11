package com.simplespace.android.lib.simple.compose.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme

@Composable
fun SimpleCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
    theme: SimpleColorTheme
) =
    SimpleCheckBox(
        modifier = modifier,
        isChecked = isChecked,
        size = size,
        padding = padding,
        contentColor = theme.content,
        backgroundColor = theme.background,
    )

@Composable
fun SimpleCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
    contentColor: Color,
    backgroundColor: Color,
) {
    SimpleToggleIcon(
        modifier = modifier,
        isSelected = isChecked,
        selectedIcon = Icons.Default.CheckBox,
        unSelectedIcon = Icons.Default.CheckBoxOutlineBlank,
        contentDescription = if (isChecked) "unselect" else "select",
        size = size,
        padding = padding,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
    )
}

@Composable
fun SimpleCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    size: Dp,
    padding: Dp = 0.dp,
) {
    SimpleToggleIcon(
        modifier = modifier,
        isSelected = isChecked,
        selectedIcon = Icons.Default.CheckBox,
        unSelectedIcon = Icons.Default.CheckBoxOutlineBlank,
        contentDescription = if (isChecked) "unselect" else "select",
        size = size,
        padding = padding,
    )
}