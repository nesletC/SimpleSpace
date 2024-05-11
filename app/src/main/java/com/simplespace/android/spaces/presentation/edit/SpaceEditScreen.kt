package com.simplespace.android.spaces.presentation.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerViewModel
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.spaces.data.SpaceSessions
import com.simplespace.android.spaces.model.SpaceMeta
import com.simplespace.android.spaces.presentation.SpaceEvent
import com.simplespace.android.spaces.presentation.edit.components.SpaceEditScreenContent

@Composable
fun AppScreenScope.SpaceEditScreen(
    viewModel: SpaceEditViewModel,
    externalFileManagerViewModel: SimpleFileManagerViewModel,
) {

    val spaceIndex = when (viewModel.purpose) {
        SpaceEvent.LaunchingSpaceEdit.New -> null
        is SpaceEvent.LaunchingSpaceEdit.Update -> viewModel.purpose.sessionIndex
    }

    val sourceMeta =
        if (spaceIndex == null) SpaceMeta() else SpaceSessions.getMeta(spaceIndex)

    if (sourceMeta == null) {
        Box(fillMaxSize) {
            Box(Modifier.align(Alignment.Center)) {
                texts.h3(text = "space timed out")
            }
        }
    }
    else
        SpaceEditScreenContent(
            sourceMeta = sourceMeta,
            viewModel = viewModel,
            externalFileManagerViewModel = externalFileManagerViewModel
        )
}