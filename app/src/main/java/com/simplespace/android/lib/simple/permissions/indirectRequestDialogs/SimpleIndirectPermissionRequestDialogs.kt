package com.simplespace.android.lib.simple.permissions.indirectRequestDialogs

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlay
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.permissions.SimplePermissionRequestsState
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.iterable.list.ImmutableList
import com.simplespace.android.lib.standard.position.Position

@Composable
fun AppScreenScope.SimpleIndirectPermissionRequestDialogs(
    state: State<SimplePermissionRequestsState>,
    dismissDialog: () -> Unit,
    openSettings: () -> Unit,
    abort: () -> Unit,
) {

    SimpleOverlay(
        visible = state.value.visibilities.visible,
        orientation = Position.Orientation.VERTICAL,
        escape = abort
    ) {

        Content(
            requestedIntents = state.value.requestedIntents,
            entries = state.value.entries,
            visibleIntent = state.value.visibilities.visibleIntent
        )

        ControlButtons(dismissDialog = dismissDialog, openSettings = openSettings)
    }
}

@Composable
private fun AppScreenScope.Content(
    requestedIntents: ImmutableList<AndroidIntent>,
    entries: ImmutableList<ImmutableList<SimpleIndirectPermissionTexts>>,
    visibleIntent: AndroidIntent
) {

    LazyRow{

        items(requestedIntents.size) { index ->

            AnimatedVisibility(
                visible =
                visibleIntent == requestedIntents[index]
            ) {

                SimpleIndirectPermissionRequestDialogTexts(
                    entries = entries[index]
                )
            }
        }
    }
}

@Composable
private fun AppScreenScope.ControlButtons(
    dismissDialog: () -> Unit,
    openSettings: () -> Unit,
) {

    Log.d("asdf", "control buttons recomposed")

    SpacerH(height = 10.dp)

    Row{

        mediumButtons.IconTextButton(icon = Icons.Default.Check, text = "Grant") {

            dismissDialog()

            openSettings()
        }

        SpacerW(width = 10.dp)

        mediumButtons.IconTextButton(
            icon = Icons.Default.Close,
            text = "Skip",
            onClick = dismissDialog
        )
    }
}