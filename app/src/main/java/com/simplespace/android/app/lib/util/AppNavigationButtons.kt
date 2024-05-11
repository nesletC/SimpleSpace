package com.simplespace.android.app.lib.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.lib.standard.composable.spacer.SpacerH

@Composable
fun AppScreenScope.AppNavigationButtons(
    navigate: (Route) -> Unit
) {

    SpacerH(height = 20.dp)

    mediumButtons.TextButton(text = "Settings") {
        navigate(Route.Settings)
    }

    SpacerH(height = 20.dp)

    mediumButtons.TextButton(text = "Tutorial") {
        navigate(Route.Welcome)
    }

    SpacerH(height = 20.dp)

    mediumButtons.TextButton(text = "About") {
        navigate(Route.About)
    }

    SpacerH(height = 20.dp)
}