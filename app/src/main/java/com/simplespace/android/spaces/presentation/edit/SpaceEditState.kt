package com.simplespace.android.spaces.presentation.edit

import androidx.compose.runtime.Immutable

@Immutable
data class SpaceEditState(
    val directories: List<SpaceDirectoryUpdateMeta> = listOf(),
    val directoryRequested: Boolean = false,
)
