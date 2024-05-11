package com.simplespace.android.lib.simple.compose.input

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.simplespace.android.lib.simple.color.SimpleColor
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.autoFill
import com.simplespace.android.res.Shapes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppScreenScope.SimpleInput(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "input",
    labelError: String = "error",
    maxLines: Int = 1,
    autoFill: List<AutofillType>? = null,
    isErrorInValue: Boolean = false,
    fadedColor: Color = Color.Gray,
    allowNumbersOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) =
    SimpleInput(
        modifier = modifier,
        value = value,
        label = label,
        labelError = labelError,
        allowNumbersOnly = allowNumbersOnly,
        maxLines = maxLines,
        autoFill = autoFill,
        isErrorInValue = isErrorInValue,
        fontSize = fontSize.font.sp,
        fadedColor = fadedColor,
        theme = theme,
        onValueChange = onValueChange,
        onSubmit = onSubmit,
    )

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleInput(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "input",
    labelError: String = "error",
    allowNumbersOnly: Boolean = false,
    maxLines : Int = 1,
    autoFill: List<AutofillType>? = null,
    isErrorInValue: Boolean = false,
    fontSize: TextUnit = 20.sp,
    fadedColor: Color = Color.Gray,
    theme: SimpleColorTheme,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) = SimpleInput(
    modifier = modifier,
    value = value,
    label = label,
    labelError = labelError,
    maxLines = maxLines,
    autoFill = autoFill,
    isErrorInValue = isErrorInValue,
    fontSize = fontSize,
    color = theme.content,
    background = theme.background,
    borderColor = theme.outline,
    fadedColor = fadedColor,
    allowNumbersOnly = allowNumbersOnly,
    onValueChange = onValueChange,
    onSubmit = onSubmit,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleInput(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "input",
    labelError: String = "error",
    maxLines : Int = 1,
    allowNumbersOnly: Boolean = false,
    autoFill: List<AutofillType>? = null,
    isErrorInValue: Boolean = false,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.Black,
    fadedColor: Color = Color.Gray,
    background: Color = Color.White,
    borderColor: Color = Color.Black,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {

    val clipBoardManager = LocalClipboardManager.current

    val errorRed = SimpleColors.simpleColor(SimpleColor.ERROR_RED)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isErrorInValue,
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType =
                    if (allowNumbersOnly)
                        KeyboardType.Number
                    else
                        KeyboardType.Text
            ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSubmit()
            }
        ),
        modifier = if (autoFill == null) modifier else modifier.autoFill(
            autoFill, onValueChange
        ),
        maxLines = maxLines,
        textStyle = TextStyle(
            fontSize = fontSize
        ),
        label = {
            Text(text = if (isErrorInValue) labelError else label)
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    clipBoardManager.setText(AnnotatedString(value))
                }
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, "Copy input to clipboard")
            }
        },
        shape = Shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = color,
            disabledTextColor = fadedColor,
            backgroundColor = background,
            cursorColor = color,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = fadedColor,
            focusedLabelColor = color,
            unfocusedLabelColor = fadedColor,
            leadingIconColor = color,
            trailingIconColor = color,
            errorBorderColor = errorRed,
            errorLabelColor = errorRed,
            errorLeadingIconColor = errorRed,
            errorTrailingIconColor = errorRed,
        ),
    )
}