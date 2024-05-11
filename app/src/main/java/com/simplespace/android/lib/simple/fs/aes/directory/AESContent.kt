package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.file.AESDirectoryOverview
import com.simplespace.android.lib.standard.io.file.FileIndicesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.MutableFileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.fileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.iterable.list.getAll
import com.simplespace.android.lib.standard.iterable.list.listOfSize

class AESContent(
    val raw: FileNamesWithDirectoryPartition = fileNamesWithDirectoryPartition(),
    val aes: AESContentDataEncrypted = AESContentDataEncrypted(),
) {

    fun getNames() = AESContentFileNames(
        raw = raw,
        aes = aes.getNames()
    )

    fun getFileNames() = AESContentFileNames(
        raw = raw,
        aes = aes.getFileNames()
    )

    fun getAllNames() = aes.getNames().let{

        MutableFileNamesWithDirectoryPartition(

            directories = it.directories.apply {
                addAll(
                    raw.directories
                )
            },
            files = it.files.apply {
                addAll(
                    raw.files
                )
            }
        )
    }

    fun getAllFileNames() = aes.getFileNames().let {

        MutableFileNamesWithDirectoryPartition(

            directories = it.directories.apply {
                addAll(
                    raw.directories
                )
            },
            files = it.files.apply {
                addAll(
                    raw.files
                )
            }
        )
    }

    fun getOverview() = AESContentOverview(
        raw = raw,
        encryptedDirectories = aes.directories.map {
            AESDirectoryOverview(
                names = it.fileOverview(),
                key = it.meta.secretKey
            )
        },
        encryptedFiles = aes.files.map {
            it.fileOverview()
        }
    )

    fun getIndices(fileNames: AESContentFileNames) = AESContentDataIndices(
        raw = raw.getSelectionIndices(fileNames.raw),
        aes = aes.getFileNames().getSelectionIndices(fileNames.aes)
    )

    fun getAllIndices() =
        AESContentDataIndices(
            raw = FileIndicesWithDirectoryPartition(
                directories = listOfSize(raw.directories.size) {
                    it
                },
                files = listOfSize(raw.files.size) {
                    it
                }
            ),
            aes = FileIndicesWithDirectoryPartition(
                directories = listOfSize(aes.directories.size) {
                    it
                },
                files = listOfSize(aes.files.size) {
                    it
                }
            ),
        )


    fun selection(indices: AESContentDataIndices) = AESContent(
        raw = rawSelection(indices.raw),
        aes = aesSelection(indices.aes)
    )

    fun rawSelection(indices: FileIndicesWithDirectoryPartition) = fileNamesWithDirectoryPartition(
        directories = raw.directories.getAll(indices.directories),
        files = raw.files.getAll(indices.files)
    )

    fun aesSelection(indices: FileIndicesWithDirectoryPartition) = AESContentDataEncrypted(
        directories = aes.directories.getAll(indices.directories),
        files = aes.files.getAll(indices.files)
    )
}
