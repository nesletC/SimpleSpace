package com.simplespace.android.lib.standard.io.file

fun fileNamesWithDirectoryPartition(
    directories: List<String> = listOf(),
    files: List<String> = listOf()
) : FileNamesWithDirectoryPartition =
    ImmutableFileNamesWithDirectoryPartition(directories, files)

data class ImmutableFileNamesWithDirectoryPartition(
    override val directories: List<String> = listOf(),
    override val files: List<String> = listOf()
) : FileNamesWithDirectoryPartition