package com.simplespace.android.lib.simple.compose.dropDown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.compose.icon.SimpleDropDownIcon
import com.simplespace.android.lib.standard.color.alpha
import com.simplespace.android.lib.standard.composable.spacer.SpacerW


@Composable
fun SimpleDropdown(
    titleModifier: Modifier = Modifier,
    dropped: MutableState<Boolean>,
    title: String,
    titleFontSize: TextUnit,
    titlePaddingHorizontal: Dp,
    titlePaddingVertical: Dp,
    dropdownIconSize: Dp,
    content: @Composable () -> Unit,
) {

    Column{
        Row(
            modifier = titleModifier
                .padding(
                    horizontal = titlePaddingHorizontal,
                    vertical = titlePaddingVertical
                )
                .clickable {
                    dropped.value = !dropped.value
                }
        ){

            SimpleDropDownIcon(isDropped = dropped.value, size = dropdownIconSize)

            SpacerW(width = 10.dp)

            Text(text = title, fontSize = titleFontSize)
        }

        Divider(modifier = Modifier.height(1.dp).background(Color.Gray.alpha(60)))

        AnimatedVisibility(visible = dropped.value) {
            content()
        }
    }
}