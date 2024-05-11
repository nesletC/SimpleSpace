package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.directory.AESContentDataIndices
import com.simplespace.android.lib.standard.io.file.FileIndicesWithDirectoryPartition
import com.simplespace.android.lib.standard.iterable.list.list

class AESFileManagerSelectionIndices (
    val aesDirectories: List<Boolean>,
    val aesFiles: List<Boolean>,
    val rawDirectories: List<Boolean>,
    val rawFiles: List<Boolean>,
    val selectedCount : Int,
    val filesCount: Int,
) {
    val allSelected = selectedCount == filesCount

    fun getIndices() = AESContentDataIndices(
        raw = FileIndicesWithDirectoryPartition(
            directories = list{
                rawDirectories.forEachIndexed { index, boolean ->
                    if (boolean)
                        add(index)
                }
            },
            files = list{
                rawFiles.forEachIndexed { index, boolean ->
                    if (boolean)
                        add(index)
                }
            },
        ),
        aes = FileIndicesWithDirectoryPartition(
            directories = list{
                aesDirectories.forEachIndexed { index, boolean ->
                    if (boolean)
                        add(index)
                }
            },
            files = list{
                aesFiles.forEachIndexed { index, boolean ->
                    if (boolean)
                        add(index)
                }
            },
        ),
    )
}