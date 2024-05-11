package com.simplespace.android.spaces.presentation.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.app.lib.screen.AppScreenWithScaffoldState
import com.simplespace.android.lib.simple.compose.input.SimplePasswordInput
import com.simplespace.android.lib.standard.storage.Storage
import com.simplespace.android.spaces.data.SpaceAccessKeys
import com.simplespace.android.spaces.data.SpaceAuthentication

@Composable
fun SpaceAuthenticationScreen(
    preferences: AppPreferencesData,
    authenticator: SpaceAuthenticationViewModel,
) {
    AppScreenWithScaffoldState(preferences = preferences) {

        SpaceAuthenticationScreen(
            authenticator = authenticator
        ) { message, actionLabel ->

            it.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
        }
    }
}

@Composable
fun AppScreenScope.SpaceAuthenticationScreen(
    authenticator: SpaceAuthenticationViewModel,
    showSnackbar: suspend (
        message: String,
        actionLabel: String?
    ) -> Unit,
) {

    val newKeyExpected = authenticator.newKeyExpected

    Column(
        modifier = Modifier
            .height(
                maxHeightCalculations
                    .dp(1000)
                    .minPercentage(60)
                    .dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val input = rememberSaveable {
            mutableStateOf("")
        }

        fun submit() {

            authenticator(input.value)
        }

        fun updateValue(newInputValue: String) {
            input.value = newInputValue
        }


        SimplePasswordInput(
            value = input.value,
            modifier = Modifier.width(
                maxWidthCalculations
                    .dp(600)
                    .maxPercentage(80)
                    .dp
            ),
            onSubmit = ::submit,
            onValueChange =
                if (newKeyExpected) ::updateValue else {
                    {
                        updateValue(it)
                        submit()
                    }
                }
        )

        if (newKeyExpected) {

            val preferredKeyFormatIs64 = rememberSaveable {
                mutableStateOf(true)
            }

            Row {
                mediumButtons.IconButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Randomly Generated Key",
                    onClick =
                        if (preferredKeyFormatIs64.value) {
                            {
                                updateValue(
                                    SpaceAccessKeys.randomKeyString64()
                                )
                            }
                        } else {
                            {
                                updateValue(
                                    SpaceAccessKeys.randomKeyString16()
                                )
                            }
                        }
                )

                mediumButtons.IconButton(
                    icon = Icons.AutoMirrored.Filled.Login,
                    contentDescription = "Confirm",
                    onClick = ::submit
                )
            }

            Row {

                Text("Format for generated key: ")

                mediumButtons.ToggleButtons(
                    toggleStates = listOf("64", "16"),
                    currentSelection = if (preferredKeyFormatIs64.value) 0 else 1
                ) {

                    preferredKeyFormatIs64.value = it == 0
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {

        val authenticationFailedMessage =
            if (newKeyExpected)
                "This key is not available"
            else
                "This key is not correct"

        authenticator.authenticationFailed.collect{

            showSnackbar(
                authenticationFailedMessage,
                "Authentication Failed",
            )
        }
    }

    LaunchedEffect(key1 = Unit) {

        authenticator.ioException.collect{

            val storageLabel = when(it.directoryStorage) {
                is Storage.Custom -> "external"
                Storage.Main.Open.App -> "app"
                Storage.Main.Open.Public -> "public"
                Storage.Main.Protected -> "protected"
            }

            showSnackbar(
                "directory " + when (it) {
                    is SpaceAuthentication.Error.IO.Creation -> "creation"
                    is SpaceAuthentication.Error.IO.Deletion -> "deletion"
                    is SpaceAuthentication.Error.IO.Key -> "key change"
                    is SpaceAuthentication.Error.IO.Rename -> "renaming"
                } + " failed in " + storageLabel,
                "IO Exception",
            )
        }
    }
}