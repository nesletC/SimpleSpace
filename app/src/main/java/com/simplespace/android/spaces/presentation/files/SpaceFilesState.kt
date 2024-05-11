package com.simplespace.android.spaces.presentation.files

import androidx.compose.runtime.Immutable
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionAction
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionPurpose

@Immutable
data class SpaceFilesState(
    val importRequested: Boolean = false,
    val importSelectionReceived: Boolean = false,
    val exportRequested: Boolean = false,
    val exportDirectoryReceived: Boolean = false,
    val selectionPurpose: AESFileManagerSelectionPurpose? = null,
    val selectionAction: AESFileManagerSelectionAction? = null
)
