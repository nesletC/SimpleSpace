package com.simplespace.android.spaces.presentation

import androidx.compose.runtime.Immutable
import com.simplespace.android.spaces.model.SpaceOverview

@Immutable
data class SpacesUIState(
    val overview: Map<Int, SpaceOverview> = mapOf(),
    val current: Int? = null
)