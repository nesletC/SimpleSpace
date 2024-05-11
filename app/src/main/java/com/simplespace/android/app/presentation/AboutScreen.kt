package com.simplespace.android.app.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.spacer.SpacerH

@Composable
fun AppScreenScope.AboutScreen() {

    SpacerH(height = 20.dp)

    Column {
        texts.h4("SimpleSpace 1.0 is under way")
    }
}