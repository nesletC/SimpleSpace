package com.simplespace.android.app.lib.screen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize

@Composable
fun AppScaffoldData.AppScreen(
    preferences: AppPreferencesData,
    paddingValues: PaddingValues,
    content: @Composable AppScreenScope.() -> Unit,
) {

    BoxWithConstraints (fillMaxSize.padding(paddingValues)) {
        appScreenScope(
            preferences = preferences,
            nightMode = nightMode,
            theme = theme,
            maxWidth = maxWidth,
            maxHeight = maxHeight
        )
            .content()
    }
}

@Composable
fun AppScreen(
    preferences: AppPreferencesData,
    content: @Composable AppScreenScope.() -> Unit,
) {

    preferences.AppScaffold {
        AppScreen(preferences = preferences, paddingValues = it, content = content)
    }
}

@Composable
fun AppScreenWithScaffoldState(
    preferences: AppPreferencesData,
    content: @Composable AppScreenScope.(ScaffoldState) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val scaffoldData = preferences.appScaffoldData()

    Scaffold (
        modifier = fillMaxSize,
        contentColor = scaffoldData.theme.surfaceText,
        backgroundColor = scaffoldData.theme.surfaceBackground,
        scaffoldState = scaffoldState
    ){

        scaffoldData.AppScreen(preferences = preferences, paddingValues = it) {

            content(scaffoldState)
        }
    }
}