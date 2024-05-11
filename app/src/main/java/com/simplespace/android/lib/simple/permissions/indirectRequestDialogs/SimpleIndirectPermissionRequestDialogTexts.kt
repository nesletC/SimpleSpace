package com.simplespace.android.lib.simple.permissions.indirectRequestDialogs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.iterable.list.ImmutableList

@Composable
fun AppScreenScope.SimpleIndirectPermissionRequestDialogTexts(
    entries: ImmutableList<SimpleIndirectPermissionTexts>,
) {

    Column {

        entries.forEach{ permissionTexts ->

            Log.d("asdf", "check for unnecessary recomposition")

            SpacerH(height = 5.dp)

            texts.h3(
                text = permissionTexts.label
            )

            SpacerH(height = 3.dp)

            texts.font(
                text = "to " + permissionTexts.description
            )
        }
    }
}