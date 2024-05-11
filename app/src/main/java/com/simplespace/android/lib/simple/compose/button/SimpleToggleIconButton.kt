package com.simplespace.android.lib.simple.compose.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.standard.composable.modifier.showIf


@Composable
fun SimpleToggleIconButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    borderWidth: Dp,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector,
    contentDescription: String? = null,
    padding: Dp,
    size: Dp,
    theme: SimpleColorTheme,
    onClick: () -> Unit,
) =
    SimpleToggleIconButton(
        modifier = modifier,
        isSelected = isSelected,
        borderWidth = borderWidth,
        selectedIcon = selectedIcon,
        unSelectedIcon = unSelectedIcon,
        contentDescription = contentDescription,
        padding = padding,
        size = size,
        contentColor = theme.content,
        backgroundColor = theme.background,
        outlineColor = theme.outline,
        onClick = onClick,
    )

@Composable
fun SimpleToggleIconButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    borderWidth: Dp,
    selectedIcon: ImageVector,
    unSelectedIcon: ImageVector,
    contentDescription: String? = null,
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

        Box(modifier.padding(padding)){
            Icon(
                modifier = modifier.requiredSize(size).showIf(isSelected),
                imageVector = selectedIcon,
                contentDescription = null,
            )

            Icon(
                modifier = modifier.requiredSize(size).showIf(!isSelected),
                imageVector = unSelectedIcon,
                contentDescription = contentDescription,
            )
        }
    }