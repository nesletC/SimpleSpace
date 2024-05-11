package com.simplespace.android.spaces.model

data class SpaceOverview (
    val name: String = "",
    val appDirectoryExists: Boolean = false,
    val customDirectories: List<String> = listOf()
)