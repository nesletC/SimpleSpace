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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.simplespace.android.lib.simple.color.SimpleColor
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.autoFill
import com.simplespace.android.res.Shapes

@Composable
fun AppScreenScope.SimplePasswordInput(
    value: String,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    autofill: Boolean = true,
    isErrorInPassword: Boolean = false,
    fadedColor: Color = Color.Gray,
    onValueChange: (String) -> Unit,
) = SimplePasswordInput(
    modifier = modifier,
    value = value,
    autoFill = autofill,
    isErrorInPassword = isErrorInPassword,
    fontSize = fontSize.font.sp,
    theme = theme,
    fadedColor = fadedColor,
    onValueChange = onValueChange,
    onSubmit = onSubmit,
)

@Composable
fun SimplePasswordInput(
    modifier: Modifier = Modifier,
    value: String,
    autoFill: Boolean = true,
    isErrorInPassword: Boolean = false,
    fontSize: TextUnit = 14.sp,
    theme: SimpleColorTheme,
    fadedColor: Color = Color.Gray,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) = SimplePasswordInput(
    modifier = modifier,
    value = value,
    autoFill = autoFill,
    isErrorInPassword = isErrorInPassword,
    fontSize = fontSize,
    color = theme.content,
    contrastColor = theme.surfaceText,
    background = theme.background,
    borderColor = theme.outline,
    fadedColor = fadedColor,
    onValueChange = onValueChange,
    onSubmit = onSubmit,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimplePasswordInput(
    modifier: Modifier = Modifier,
    value: String,
    autoFill: Boolean = false,
    isErrorInPassword: Boolean = false,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.Black,
    contrastColor: Color = Color.Black,
    background: Color = Color.White,
    borderColor: Color = Color.Black,
    fadedColor: Color = Color.Gray,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {

    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    val imageShowPassword = Icons.Filled.Visibility
    val imageHidePassword = Icons.Filled.VisibilityOff

    val descriptionHidePassword = "Hide password"
    val descriptionShowPassword = "Show password"

    val labelError = "try again"
    val labelRegular = "password"

    val clipboardManager = LocalClipboardManager.current

    val errorRed = SimpleColors.simpleColor(SimpleColor.ERROR_RED)

    OutlinedTextField(
        value = value,
        modifier =
            if (autoFill)
                modifier
                    .autoFill(
                        listOf(
                            AutofillType.Password
                        ), onValueChange
                    )
            else
                modifier,
        onValueChange = {
            onValueChange(it)
        },
        enabled = true,
        readOnly = false,
        textStyle = TextStyle(
            fontSize = fontSize
        ),
        label = {
            Text(text = if (isErrorInPassword) labelError else labelRegular, fontSize = fontSize)
        },
        placeholder = {
            Text(text = "password", fontSize = fontSize)
        },
        leadingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {

                Icon(
                    imageVector =
                        if (passwordVisible)
                            imageHidePassword
                        else
                            imageShowPassword,
                    contentDescription =
                        if (passwordVisible)
                            descriptionHidePassword
                        else
                            descriptionShowPassword
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(value))
                }) {
                Icon(imageVector = Icons.Default.ContentCopy, "Copy password to clipboard")
            }
        },
        visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
        isError = isErrorInPassword,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSubmit()
            }
        ),
        singleLine = true,
        shape = Shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = color,
            disabledTextColor = fadedColor,
            backgroundColor = background,
            cursorColor = color,
            errorCursorColor = color,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            leadingIconColor = if (passwordVisible) fadedColor else contrastColor,
            trailingIconColor = if (value.isBlank()) fadedColor else contrastColor,
            focusedLabelColor = contrastColor,
            unfocusedLabelColor = fadedColor,
            errorLabelColor = errorRed,
            errorBorderColor = errorRed,
            placeholderColor = fadedColor,
        )
    )
}