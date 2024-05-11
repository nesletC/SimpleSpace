package com.simplespace.android.lib.standard.io.file

import com.simplespace.android.lib.standard.iterable.list.getAll
import com.simplespace.android.lib.standard.iterable.list.list

interface FileNamesWithDirectoryPartition {

    val directories: List<String>
    val files: List<String>


    fun getSelection(selection: FileIndicesWithDirectoryPartition) :
            MutableFileNamesWithDirectoryPartition =
        MutableFileNamesWithDirectoryPartition(
            directories = directories.getAll(selection.directories),
            files = files.getAll(selection.files)
        )

    fun getSelectionExclusive(unSelectedIndices: FileIndicesWithDirectoryPartition) :
            MutableFileNamesWithDirectoryPartition =
        MutableFileNamesWithDirectoryPartition(
            directories = directories.toMutableList(),
            files = files.toMutableList(),
        )
            .apply {
                unSelectedIndices.directories.forEach {
                    directories.removeAt(it)
                }
                unSelectedIndices.files.forEach {
                    files.removeAt(it)
                }
            }

    fun getSelectionIndices(selection: FileNamesWithDirectoryPartition) = FileIndicesWithDirectoryPartition(

        directories = list{

            selection.directories.forEach{

                val index = directories.indexOf(it)

                if (index != -1)
                    add(index)
            }
        },
        files = list{

            selection.files.forEach{

                val index = files.indexOf(it)

                if (index != -1)
                    add(index)
            }
        }
    )
}