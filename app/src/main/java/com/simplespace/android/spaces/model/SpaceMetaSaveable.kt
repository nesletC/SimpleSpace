package com.simplespace.android.spaces.model

import com.simplespace.android.lib.standard.storage.Storages
import kotlinx.serialization.Serializable

@Serializable
data class SpaceMetaSaveable (
    val realName: String,
    val maxTimeoutInMinutes: Int,
    val protectedDirectory: SpaceDirectoryAccess,
    val appDirectory: SpaceDirectoryAccess?,
    val directories: List<SpaceDirectorySaveable>,
) {
    fun meta() = SpaceMeta(
        realName = realName,
        maxTimeoutInMinutes = maxTimeoutInMinutes,
        protectedDirectory = protectedDirectory,
        appDirectory = appDirectory,
        directories = directories.map {
            SpaceDirectory(
                rootDirectoryIndex = Storages.indexOf(it.rootDirectoryPath),
                parentList = it.parentList,
                label = it.label,
                access = it.access,
            )
        }
    )
}