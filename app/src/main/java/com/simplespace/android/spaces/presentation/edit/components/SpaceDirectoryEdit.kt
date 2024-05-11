package com.simplespace.android.spaces.presentation.edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.compose.input.SimpleInput
import com.simplespace.android.lib.simple.compose.input.SimplePasswordInput
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.string.nullIfBlank
import com.simplespace.android.spaces.data.SpaceAccessKeys
import com.simplespace.android.spaces.presentation.edit.SpaceDirectoryUpdateMeta


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppScreenScope.SpaceDirectoryEdit(
    moveOn: () -> Unit,
    updateData: SpaceDirectoryUpdateMeta,
    isLabelEditable: Boolean,
) {

    updateData.run {

        Column {

            val directoryFileNameInput = rememberSaveable {
                mutableStateOf(defaultFileName)
            }

            fileName.value =
                directoryFileNameInput.value.nullIfBlank()?:defaultFileName

            val fileNameInputLabel = "fileName"

            SimpleInput(
                value = directoryFileNameInput.value,
                label = fileNameInputLabel,
                labelError = fileNameInputLabel,
                isErrorInValue = directoryFileNameInput.value.isBlank(),
                onValueChange = {
                    directoryFileNameInput.value = it
                },
                onSubmit = moveOn,
            )

            SpacerH(height = 10.dp)

            val directoryLabelInput = rememberSaveable {
                mutableStateOf(defaultLabel)
            }

            if (isLabelEditable) {

                val labelInputLabel = "name"

                SimpleInput(
                    value = directoryLabelInput.value,
                    onSubmit = moveOn,
                    label = labelInputLabel,
                    labelError = labelInputLabel,
                    isErrorInValue = directoryLabelInput.value.isBlank(),
                    onValueChange = {

                        directoryLabelInput.value = it

                        label.value = it.nullIfBlank()?:defaultLabel
                    }
                )

                SpacerH(height = 10.dp)
            }

            val directoryKeyFormatIs64 = rememberSaveable {
                mutableStateOf(true)
            }

            val defaultDirectoryKeyString =
                SpaceAccessKeys.directoryKeyToString(defaultKey, true)

            val isErrorInKey = rememberSaveable {
                mutableStateOf(false)
            }

            val directoryKeyString = rememberSaveable {
                mutableStateOf(
                    defaultDirectoryKeyString
                )
            }

            SimplePasswordInput(
                value = directoryKeyString.value,
                onSubmit = moveOn,
                autofill = false,
                isErrorInPassword = isErrorInKey.value
            ) {
                directoryKeyString.value = it

                val nullableKey = SpaceAccessKeys.validatedDirectoryKey(it)
                key.value = nullableKey?:defaultKey

                isErrorInKey.value = nullableKey == null
            }

            SpacerH(height = 5.dp)

            Row{

                mediumButtons.IconButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Random"
                ) {
                    key.value = SimpleEncryption.randomKey()

                    directoryKeyString.value =
                        SpaceAccessKeys.directoryKeyToString(
                            key.value, directoryKeyFormatIs64.value
                        )
                }

                texts.font(text = "Format: ")

                mediumButtons.ToggleButtons(
                    toggleStates = listOf("64", "16"),
                    currentSelection = if (directoryKeyFormatIs64.value) 0 else 1
                ) {

                    val isFormat64 = it == 0

                    directoryKeyFormatIs64.value = isFormat64

                    directoryKeyString.value = SpaceAccessKeys.directoryKeyToString(
                        key.value,
                        isFormat64
                    )
                }

                mediumButtons.IconButton(
                    icon = Icons.Default.Close,
                    contentDescription = "Reset to default"
                ) {
                    label.value = defaultLabel
                    key.value = defaultKey
                    fileName.value = defaultFileName

                    directoryFileNameInput.value = defaultFileName
                    directoryKeyString.value =
                        SpaceAccessKeys.directoryKeyToString(defaultKey, directoryKeyFormatIs64.value)
                    directoryLabelInput.value = defaultLabel
                }
            }

            SpacerH(height = 10.dp)
        }
    }
}