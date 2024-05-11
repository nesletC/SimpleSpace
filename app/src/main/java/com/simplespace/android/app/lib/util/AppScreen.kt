package com.simplespace.android.app.lib.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlay
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.lib.standard.composable.spacer.SpacerW

@Composable
fun AppScreenScope.AppScreen(
    title: String,
    navigate: (Route) -> Unit,
    content: @Composable () -> Unit,
) {

    val appNavigationOverlayVisible = rememberSaveable {
        mutableStateOf(false)
    }

    Column (
        fillMaxSize
    ){
        Row(
            modifier = Modifier
                .width(maxWidthCalculations
                    .inch(8)
                    .maxPercentage(100)
                    .dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            SpacerW(width = 1.dp)

            texts.title(text = title)

            SpacerW(width = 1.dp)

            mediumButtons.IconButton(icon = Icons.Default.Settings, contentDescription = "Settings") {
                appNavigationOverlayVisible.value = true
            }
        }

        Box{
            content()
        }
    }


    SimpleOverlay(
        visible = appNavigationOverlayVisible.value,
        background = theme.surfaceBackground,
        escape = {
            appNavigationOverlayVisible.value = false
        }
    ) {

        AppNavigationButtons(navigate = navigate)
    }
}