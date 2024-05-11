package com.simplespace.android.lib.simple.compose.uiSets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.icon.SimpleCheckBox
import com.simplespace.android.lib.simple.compose.icon.SimpleDropDownIcon
import com.simplespace.android.lib.simple.compose.icon.SimpleIcon
import com.simplespace.android.lib.simple.compose.icon.SimpleToggleIcon

@Immutable
class SimpleFixedSizeIcons(
    private val theme: SimpleColorTheme,
    private val size: Dp,
) {
    @Composable
    fun Icon(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        contentDescription: String? = null,
    ) =
        SimpleIcon(
            modifier = modifier,
            icon = icon,
            size = size,
            contentDescription = contentDescription,
            theme = theme,
        )

    @Composable
    fun CheckBox(
        modifier: Modifier = Modifier,
        isChecked: Boolean,
        padding: Dp = 0.dp,
    ) = SimpleCheckBox(
        modifier = modifier,
        isChecked = isChecked,
        size = size,
        padding = padding,
        theme = theme
    )

    @Composable
    fun DropDownIcon(
        modifier: Modifier = Modifier,
        isDropped: Boolean,
        padding: Dp = 0.dp,
    ) =
        SimpleDropDownIcon(
            modifier = modifier,
            isDropped = isDropped,
            size = size,
            padding = padding,
            theme = theme
        )

    @Composable
    fun ToggleIcon(
        modifier: Modifier = Modifier,
        isSelected: Boolean,
        selectedIcon: ImageVector,
        unSelectedIcon: ImageVector,
        contentDescription: String? = null,
        padding: Dp = 0.dp,
    ) =
        SimpleToggleIcon(
            modifier = modifier,
            isSelected = isSelected,
            selectedIcon = selectedIcon,
            unSelectedIcon = unSelectedIcon,
            contentDescription = contentDescription,
            size = size,
            padding = padding,
            theme = theme
        )
}