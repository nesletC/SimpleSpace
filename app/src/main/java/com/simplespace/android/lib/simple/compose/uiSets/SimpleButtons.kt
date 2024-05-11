package com.simplespace.android.lib.simple.compose.uiSets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.button.SimpleButton
import com.simplespace.android.lib.simple.compose.button.SimpleIconButton
import com.simplespace.android.lib.simple.compose.button.SimpleIconTextButton
import com.simplespace.android.lib.simple.compose.button.SimpleTextButton
import com.simplespace.android.lib.simple.compose.button.SimpleToggleButtons
import com.simplespace.android.lib.simple.compose.button.SimpleToggleIconButton
import com.simplespace.android.lib.standard.position.Position

@Immutable
class SimpleButtons (
    private val theme: SimpleColorTheme,
    private val borderWidth: Dp,
){
    @Composable
    fun Button(
        modifier: Modifier = Modifier,
        paddingHorizontal: Dp,
        paddingVertical: Dp,
        onClick: () -> Unit,
        content: @Composable RowScope.() -> Unit,
    ) = SimpleButton(
        modifier = modifier,
        borderWidth = borderWidth,
        theme = theme,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        onClick = onClick,
        content = content
    )

    @Composable
    fun TextButton(
        modifier: Modifier = Modifier,
        text: String,
        fontSize: TextUnit,
        paddingVertical: Dp,
        paddingHorizontal: Dp,
        onClick: () -> Unit
    ) = SimpleTextButton(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        paddingVertical = paddingVertical,
        paddingHorizontal = paddingHorizontal,
        borderWidth = borderWidth,
        onClick = onClick,
        theme = theme
    )

    @Composable
    fun IconButton(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        contentDescription: String,
        padding: Dp,
        size: Dp,
        onClick: () -> Unit,
    ) = SimpleIconButton(
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        padding = padding,
        size = size,
        borderWidth = borderWidth,
        onClick = onClick,
        theme = theme
    )
    @Composable
    fun IconTextButton(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        contentDescription: String? = null,
        iconSize: Dp,
        fontSize: TextUnit,
        text: String,
        padding: Dp,
        onClick: () -> Unit,
    ) = SimpleIconTextButton(
        modifier = modifier,
        borderWidth = borderWidth,
        icon = icon,
        contentDescription = contentDescription,
        iconSize = iconSize,
        fontSize = fontSize,
        text = text,
        padding = padding,
        theme = theme,
        onClick = onClick,
    )

    @Composable
    fun ToggleIconButton(
        modifier: Modifier = Modifier,
        isSelected: Boolean,
        selectedIcon: ImageVector,
        unSelectedIcon: ImageVector,
        contentDescription: String? = null,
        padding: Dp,
        size: Dp,
        onClick: () -> Unit,
    ) = SimpleToggleIconButton(
        modifier = modifier,
        isSelected = isSelected,
        borderWidth = borderWidth,
        selectedIcon = selectedIcon,
        unSelectedIcon = unSelectedIcon,
        contentDescription = contentDescription,
        padding = padding,
        size = size,
        theme = theme,
        onClick = onClick
    )

    @Composable
    fun ToggleButtons(
        toggleStates: List<String>,
        orientation: Position.Orientation = Position.Orientation.HORIZONTAL,
        currentSelection: Int = 0,
        fontSize: TextUnit,
        paddingHorizontal: Dp,
        paddingVertical: Dp,
        onToggleChange: (Int) -> Unit
    ) = SimpleToggleButtons(
        toggleStates = toggleStates,
        orientation = orientation,
        currentSelection = currentSelection,
        borderWidth = borderWidth,
        fontSize = fontSize,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        theme = theme,
        onToggleChange = onToggleChange
    )

    fun fixedSize(
        size: Dp,
        fontSize: TextUnit,
        paddingHorizontal: Dp = size / 3,
        paddingVertical: Dp = size / 3,
    ) = SimpleFixedSizeButtons(
        theme = theme,
        borderWidth = borderWidth,
        size = size,
        fontSize = fontSize,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical
    )
}