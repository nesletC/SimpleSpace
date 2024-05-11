package com.simplespace.android.app.lib.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize



@Composable
fun AppPreferencesData.appScaffoldData() : AppScaffoldData {

    val nightMode = actualNightMode(isSystemInDarkTheme())

    val theme = theme(nightMode)

    return AppScaffoldData(
        nightMode = nightMode,
        theme = theme,
    )
}

@Composable
fun AppPreferencesData.AppScaffold(
    content: @Composable AppScaffoldData.(PaddingValues) -> Unit,
) {

    appScaffoldData().run{

        Scaffold(
            modifier = fillMaxSize,
            contentColor = theme.surfaceText,
            backgroundColor = theme.surfaceBackground,
        ) {
            content(it)
        }
    }
}