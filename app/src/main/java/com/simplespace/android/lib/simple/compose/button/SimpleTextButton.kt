package com.simplespace.android.lib.simple.compose.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme

@Composable
fun SimpleTextButton(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    text: String,
    fontSize: TextUnit,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    theme: SimpleColorTheme,
    onClick: () -> Unit,
) =
    SimpleTextButton(
        modifier = modifier,
        borderWidth = borderWidth,
        text = text,
        fontSize = fontSize,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        contentColor = theme.content,
        backgroundColor = theme.background,
        outlineColor = theme.outline,
        onClick = onClick,
    )

@Composable
fun SimpleTextButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    contentColor: Color,
    backgroundColor: Color,
    outlineColor: Color,
    borderWidth: Dp,
    onClick: () -> Unit,
) =
    SimpleButton(
        modifier = modifier,
        borderWidth = borderWidth,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        outlineColor = outlineColor,
        onClick = onClick,
    ) {

        Text(text = text, fontSize = fontSize)
    }