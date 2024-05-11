package com.simplespace.android.app.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW

@Composable
fun AppScreenScope.WelcomeScreen(
    navigate: (Route) -> Unit,
    showTutorial: Boolean,
    changeShowingTutorialPreference: (Boolean) -> Unit,
) {

    Column {

        SpacerH(height = 20.dp)

        Row {
            SpacerW(width = 10.dp)
            texts.title("Welcome")
        }

        SpacerH(height = 20.dp)

        Row(fillMaxWidth) {
            Row(
                Modifier
                    .clickable {
                        changeShowingTutorialPreference(!showTutorial)
                    }
                    .padding(10.dp)
            ) {

                mediumIcons.CheckBox(isChecked = showTutorial)

                SpacerW(10.dp)

                texts.h2(text = "Always show tutorial on app start")
            }
        }

        SpacerH(height = 20.dp)

        Row(
            modifier = fillMaxWidth,
            horizontalArrangement = Arrangement.Center
        ) {
            buttons.TextButton(
                text = "Go to spaces",
                fontSize = fontSize.h1.sp,
                paddingVertical = 5.dp,
                paddingHorizontal = 10.dp
            ) {
                navigate(Route.Space)
            }
        }
    }
}