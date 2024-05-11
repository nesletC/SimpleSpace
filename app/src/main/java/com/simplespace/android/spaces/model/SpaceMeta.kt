package com.simplespace.android.spaces.model

import android.util.Log
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.standard.comparison.Findable
import com.simplespace.android.spaces.data.SpaceStorages

data class SpaceMeta(
    val realName: String = "UnnamedSpace",
    val maxTimeoutInMinutes: Int = 5,
    val protectedDirectory: SpaceDirectoryAccess = SpaceDirectoryAccess(),
    val appDirectory: SpaceDirectoryAccess? = null,
    val directories: List<SpaceDirectory> = listOf(),
) : Findable<SpaceMeta> {

    fun protectedDirectoryFile() =
        SpaceStorages.getProtectedDirectory(protectedDirectory.fileName)

    fun appDirectoryFile() : SimpleMutableFile? {
        return SpaceStorages.getAppDirectory(appDirectory?.fileName ?: return null)
    }

    fun rootDirectoryFile(index: Int) =
        directories[index].run{
            mutableFile()
        }

    fun saveable() = SpaceMetaSaveable(
        realName = realName,
        maxTimeoutInMinutes = maxTimeoutInMinutes,
        protectedDirectory = protectedDirectory,
        appDirectory = appDirectory,
        directories = directories.map {
            it.saveable()
        }
    )

    override fun equalsOther(other: SpaceMeta): Boolean =
        protectedDirectory.fileName == other.protectedDirectory.fileName
}
