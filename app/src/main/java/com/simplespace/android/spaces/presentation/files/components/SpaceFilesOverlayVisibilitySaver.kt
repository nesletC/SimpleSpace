package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver

val SpaceFilesOverlayVisibilitySaver = listSaver(
    save = {
        it.value.run {
            listOf(
                selectionPurpose,
                selectionAction,
                launchingImport,
                launchingExport
            )
        }
    },
    restore = {
        mutableStateOf(
            SpaceFilesOverlayVisibilityStates(
                selectionPurpose = it[0],
                selectionAction = it[1],
                launchingImport = it[2],
                launchingExport = it[3]
            )
        )
    }
)