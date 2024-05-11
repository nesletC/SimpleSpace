package com.simplespace.android.lib.simple.compose.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.lib.standard.position.Position.Orientation

@Composable
fun SimpleToggleButtons(
    toggleStates: List<String>,
    orientation: Orientation = Orientation.HORIZONTAL,
    currentSelection: Int = 0,
    borderWidth: Dp,
    fontSize: TextUnit,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    theme: SimpleColorTheme,
    onToggleChange: (Int) -> Unit
) =
    SimpleToggleButtons(
        toggleStates = toggleStates,
        currentSelection = currentSelection,
        orientation = orientation,
        borderWidth = borderWidth,
        fontSize = fontSize,
        textColor = theme.surfaceText,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        backgroundColor = theme.surfaceBackground,
        selectedBackgroundColor = theme.background,
        selectedTextColor = theme.content,
        selectedBorderColor = theme.outline,
        onToggleChange = onToggleChange
    )


@Composable
fun SimpleToggleButtons(
    toggleStates: List<String>,
    currentSelection: Int = 0,
    orientation: Orientation,
    borderWidth: Dp,
    fontSize: TextUnit,
    textColor: Color,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    backgroundColor: Color,
    selectedBackgroundColor: Color,
    selectedTextColor: Color,
    selectedBorderColor: Color,
    onToggleChange: (Int) -> Unit,
) {
    @Composable fun toggleStates() {
        toggleStates.forEachIndexed{ index, toggleState ->

            val isSelected = index == currentSelection

            Box(
                modifier = Modifier
                    .background(
                        if (isSelected) selectedBorderColor
                        else backgroundColor
                    )
                    .toggleable(
                        value = isSelected,
                        enabled = true,
                        onValueChange = { selected ->
                            if (selected) {
                                onToggleChange(index)
                            }
                        }
                    )
            ) {
                Box(
                    Modifier
                        .padding(borderWidth)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected)
                                    selectedBackgroundColor
                                else
                                    backgroundColor
                            )
                            .padding(vertical = paddingVertical, horizontal = paddingHorizontal)
                    ) {
                        Text(
                            toggleState,
                            fontSize = fontSize,
                            color =
                                if (isSelected)
                                    selectedTextColor
                                else
                                    textColor
                        )
                    }
                }
            }
        }
    }

    if (orientation == Orientation.HORIZONTAL)
        Row {
            toggleStates()
        }
    else
        Column {
            toggleStates()
        }
}