package com.simplespace.android.spaces.presentation.edit.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.spaces.presentation.edit.SpaceEditState

@Composable
fun AppScreenScope.SpaceDirectoriesEdit(
    state: SpaceEditState,
    moveOn: () -> Unit,
) {

    state.directories.forEach{

        SpaceDirectoryEditCancellable(
            moveOn = moveOn,
            updateData = it,
            isLabelEditable = true,
        )

        SpacerH(height = 10.dp)
    }

}