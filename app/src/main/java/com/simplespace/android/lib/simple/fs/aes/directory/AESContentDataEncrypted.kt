package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.file.AESData
import com.simplespace.android.lib.standard.io.file.MutableFileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.iterable.list.mapMutable

class AESContentDataEncrypted(
    val directories: List<AESData> = listOf(),
    val files: List<AESData> = listOf(),
) {
    fun getFileNames() = MutableFileNamesWithDirectoryPartition(
        directories = directories.mapMutable{
            it.fileName
        },
        files = files.mapMutable {
            it.fileName
        }
    )

    fun getNames() = MutableFileNamesWithDirectoryPartition(
        directories = directories.mapMutable{
            it.meta.realName
        },
        files = files.mapMutable{
            it.meta.realName
        }
    )
}