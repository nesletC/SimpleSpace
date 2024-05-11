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
class SimpleFixedSizeButtons (
    private val theme: SimpleColorTheme,
    private val borderWidth: Dp,
    private val size: Dp,
    private val fontSize: TextUnit,
    private val paddingHorizontal: Dp,
    private val paddingVertical: Dp,
) {

    @Composable
    fun Button(
        modifier: Modifier = Modifier,
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
        onClick: () -> Unit,
    ) = SimpleIconButton(
        modifier = modifier,
        icon = icon,
        contentDescription = contentDescription,
        padding = paddingVertical,
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
        text: String,
        onClick: () -> Unit,
    ) = SimpleIconTextButton(
        modifier = modifier,
        borderWidth = borderWidth,
        icon = icon,
        contentDescription = contentDescription,
        iconSize = size,
        fontSize = fontSize,
        text = text,
        padding = paddingVertical,
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
        onClick: () -> Unit,
    ) = SimpleToggleIconButton(
        modifier = modifier,
        isSelected = isSelected,
        borderWidth = borderWidth,
        selectedIcon = selectedIcon,
        unSelectedIcon = unSelectedIcon,
        contentDescription = contentDescription,
        padding = paddingVertical,
        size = size,
        theme = theme,
        onClick = onClick
    )

    @Composable
    fun ToggleButtons(
        toggleStates: List<String>,
        orientation: Position.Orientation = Position.Orientation.HORIZONTAL,
        currentSelection: Int = 0,
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
}