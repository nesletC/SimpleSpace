package com.simplespace.android.spaces.presentation.files.components

data class SpaceFilesOverlayVisibilityStates(
    val selectionPurpose: Boolean = false,
    val selectionAction: Boolean = false,
    val launchingImport: Boolean = false,
    val launchingExport: Boolean = false,
)