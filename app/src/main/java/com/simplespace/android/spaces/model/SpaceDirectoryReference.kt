package com.simplespace.android.spaces.model

sealed class SpaceDirectoryReference {

    data object Protected: SpaceDirectoryReference()
    data object App: SpaceDirectoryReference()

    class Custom(val index: Int) : SpaceDirectoryReference()
}