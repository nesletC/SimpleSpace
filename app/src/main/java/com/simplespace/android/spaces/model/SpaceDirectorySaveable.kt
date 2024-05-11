package com.simplespace.android.spaces.model

import com.simplespace.android.lib.standard.storage.Storages
import kotlinx.serialization.Serializable

@Serializable
data class SpaceDirectorySaveable(
    val rootDirectoryPath: String,
    val parentList: List<String>,
    val label: String,
    val access: SpaceDirectoryAccess,
) {
    fun directory() =
        SpaceDirectory(
            rootDirectoryIndex = Storages.indexOf(rootDirectoryPath),
            parentList = parentList,
            label = label,
            access = access,
        )
}