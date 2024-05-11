package com.simplespace.android.lib.simple.compose.uiSets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.simple.compose.icon.SimpleCheckBox
import com.simplespace.android.lib.simple.compose.icon.SimpleDropDownIcon
import com.simplespace.android.lib.simple.compose.icon.SimpleIcon
import com.simplespace.android.lib.simple.compose.icon.SimpleToggleIcon

@Immutable
class SimpleIcons (
    val theme: SimpleColorTheme
) {
    fun fixedSize(
        size: Dp,
    ) =
        SimpleFixedSizeIcons(theme, size)

    @Composable
    fun Icon(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        size: Dp,
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
        size: Dp,
        padding: Dp,
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
        size: Dp,
        padding: Dp,
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
        size: Dp,
        padding: Dp,
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