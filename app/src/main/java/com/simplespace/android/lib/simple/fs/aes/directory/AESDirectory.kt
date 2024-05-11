package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.saveMeta
import com.simplespace.android.lib.simple.fs.aes.file.AESData
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.moveRecursively
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.onChild
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.security.SimpleEncryption.decrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.encrypt
import com.simplespace.android.lib.standard.io.file.AlternativeFileName
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition

object AESDirectory {

    fun AESContentNavigation.moveRecursively(
        target: AESContentNavigation,
        overwrite: Boolean,
        copy: Boolean = true,
        selection: AESContent,
    ) {

        moveRecursively(
            target = target,
            copy = copy,
            overwrite = overwrite,
            selection = selection.getAllFileNames()
        )

        target.onDirectoryKeyFolder {

            onDirectoryKeyFolder {

                selection.aes.files.forEach {

                    AESDirectoryKeyDirectory.getAESMetaFile(
                        directoryKeyDirectory = target,
                        sourceFileName = it.fileName
                    ).saveMeta(
                        it.meta,
                        target.key
                    )

                    if (!copy)
                        AESDirectoryKeyDirectory.getAESMetaFile(
                            directoryKeyDirectory = this,
                            sourceFileName = it.fileName
                        ).delete()
                }

                selection.aes.directories.forEach {

                    AESDirectoryKeyDirectory.getAESMetaFile(
                        directoryKeyDirectory = target,
                        sourceFileName = it.fileName
                    ).saveMeta(
                        it.meta,
                        target.key
                    )

                    if (!copy)
                        AESDirectoryKeyDirectory.getAESMetaFile(
                            directoryKeyDirectory = this,
                            sourceFileName = it.fileName
                        ).delete()
                }
            }
        }
    }


    fun AESContentNavigation.encryptRecursively(
        target: AESContentNavigation,
        overwrite: Boolean = false,
        copy: Boolean = true,
        selection: AESContent = aesContent,
    ) {
        encryptRecursively(
            target = target,
            overwrite = overwrite,
            copy = copy,
            selection = selection.raw
        )

        moveRecursively(
            target = target,
            overwrite = overwrite,
            copy = copy,
            selection = AESContent(
                aes = selection.aes
            )
        )
    }

    fun SimpleMutableFile.encryptRecursively(
        target: AESContentNavigation,
        overwrite: Boolean = false,
        copy: Boolean = true,
        selection: FileNamesWithDirectoryPartition = content
    ) {

        fun encryptFile(
            childFileName: String,
            targetFileData: AESData,
            create: Boolean,
        ) {

            onChild(childFileName){

                target.onChild(targetFileData.fileName) {

                    file.encrypt(targetFileData.meta.secretKey, target.file)

                    if(!copy)
                        file.delete()
                }
            }

            if (create)
                target.saveChildMeta(targetFileData)
        }

        fun encryptUniqueFile(
            childFileName: String,
            targetFileNames: MutableList<String>
        ) {

            val data = AESFileSystem.randomAESData(
                realName = childFileName,
                fileNameBlackList = targetFileNames
            )

            targetFileNames.add(data.fileName)

            encryptFile(
                childFileName = childFileName,
                targetFileData = data,
                create = true,
            )
        }

        fun encryptFile(
            childFileName: String,
            targetFileNames: MutableList<String>,
            targetRealFileNames: MutableList<String>,
        ) {

            val targetIndex = targetRealFileNames.indexOf(childFileName)

            if (targetIndex == -1)
                encryptUniqueFile(childFileName, targetFileNames)

            else {

                val data = if (overwrite)
                    target.aesContent.aes.files[targetIndex]
                else
                    AESFileSystem.randomAESData(
                        realName = AlternativeFileName.file(
                            fileName = childFileName,
                            blackList = targetRealFileNames
                        ),
                        fileNameBlackList = targetFileNames,
                        isDirectory = false
                    )

                if (!overwrite){

                    targetRealFileNames.add(data.meta.realName)
                    targetFileNames.add(data.fileName)
                }

                encryptFile(
                    childFileName = childFileName,
                    targetFileData = data,
                    create = !overwrite
                )
            }
        }

        fun encryptDirectory(
            childFileName: String,
            targetDirectoryData: AESData,
            create: Boolean,
            recursion: () -> Unit
        ) {

            onChild(childFileName) {

                target.onChild(targetDirectoryData.fileName) {

                    if (create)
                        target.file.mkdir()

                    recursion()

                    if(!copy)
                        file.delete()
                }
            }

            if (create)
                target.saveChildMeta(targetDirectoryData)
        }

        fun encryptUniqueDirectory(
            childFileName: String,
            targetDirectoryFileNames: MutableList<String>,
            recursion: () -> Unit,
        ) {

            val data = AESFileSystem.randomAESData(
                realName = childFileName,
                fileNameBlackList = targetDirectoryFileNames
            )

            targetDirectoryFileNames.add(data.fileName)

            encryptDirectory(
                childFileName = childFileName,
                targetDirectoryData = data,
                create = true,
                recursion = recursion
            )
        }

        fun emptyDirectoryRecursion() {

            val targetFileNames = target.getMutableContent()

            content.files.forEach{
                encryptUniqueFile(
                    childFileName = it,
                    targetFileNames = targetFileNames.files,

                )
            }
            content.directories.forEach{
                encryptUniqueDirectory(
                    childFileName = it,
                    targetDirectoryFileNames = targetFileNames.directories,
                    recursion = ::emptyDirectoryRecursion,
                )
            }
        }

        fun encryptDirectory(
            childFileName: String,
            targetDirectoryNames: MutableList<String>,
            targetRealNames: MutableList<String>,
            recursion: () -> Unit,
        ) {

            val targetIndex = targetRealNames.indexOf(childFileName)

            if (targetIndex == -1)

                encryptUniqueDirectory(
                    childFileName = childFileName,
                    targetDirectoryFileNames = targetDirectoryNames,
                    recursion = ::emptyDirectoryRecursion
                )

            else {

                val data = if (overwrite)
                    target.aesContent.aes.directories[targetIndex]
                else
                    AESFileSystem.randomAESData(
                        realName = AlternativeFileName.file(
                            fileName = childFileName,
                            blackList = targetRealNames
                        ),
                        fileNameBlackList = targetDirectoryNames,
                        isDirectory = true,
                    )

                if (!overwrite) {

                    targetRealNames.add(data.meta.realName)
                    targetDirectoryNames.add(data.fileName)
                }

                encryptDirectory(
                    childFileName = childFileName,
                    targetDirectoryData = data,
                    create = !overwrite,
                    recursion = if (overwrite) recursion else ::emptyDirectoryRecursion
                )
            }
        }

        fun recursion() {

            val targetFileNames = target.getMutableContent()
            val targetRealNames = target.aesContent.getAllNames()

            content.files.forEach{
                encryptFile(
                    childFileName = it,
                    targetFileNames = targetFileNames.files,
                    targetRealFileNames = targetRealNames.files
                )
            }

            content.directories.forEach{
                encryptDirectory(
                    childFileName = it,
                    targetDirectoryNames = targetFileNames.directories,
                    targetRealNames = targetRealNames.directories,
                    recursion = ::recursion
                )
            }
        }

        val targetFileNames = target.getMutableContent()
        val targetRealNames = target.aesContent.getAllNames()

        selection.files.forEach{
            encryptFile(
                childFileName = it,
                targetFileNames = targetFileNames.files,
                targetRealFileNames = targetRealNames.files
            )
        }

        selection.directories.forEach{
            encryptDirectory(
                childFileName = it,
                targetDirectoryNames = targetFileNames.directories,
                targetRealNames = targetRealNames.directories,
                recursion = ::recursion
            )
        }
    }

    fun AESContentNavigation.decryptRecursively(
        target: SimpleMutableFile,
        overwrite: Boolean,
        copy: Boolean,
        selection: AESContent,
    ) {
        decryptRecursively(
            target = target,
            overwrite = overwrite,
            copy = copy,
            selection = selection.aes
        )

        moveRecursively(
            target = target,
            overwrite = overwrite,
            copy = copy,
            selection = selection.raw
        )
    }

    fun AESContentNavigation.decryptRecursively(
        target: SimpleMutableFile,
        overwrite: Boolean = false,
        copy: Boolean = true,
        selection: AESContentDataEncrypted
    ) {

        fun decryptFile(
            fileData: AESData,
            targetFileName: String,
        ) {

            onChild(fileData.fileName){

                target.onChild(targetFileName) {

                    file.decrypt(fileData.meta.secretKey, target.file)

                    if(!copy)
                        file.delete()
                }
            }

            if (!copy)
                onDirectoryKeyFolder {
                    AESDirectoryKeyDirectory.getAESMetaFile(this, fileData.fileName)
                        .delete()
                }
        }

        fun decryptUniqueFile(
            fileData: AESData,
        ) = decryptFile(
                fileData, fileData.fileName
            )

        fun decryptFile(
            fileData: AESData,
            targetFileNames: MutableList<String>
        ) {
            if (overwrite || !targetFileNames.contains(fileData.meta.realName))
                decryptUniqueFile(
                    fileData = fileData,
                )
            else{

                val targetFileName = AlternativeFileName.file(
                    fileData.fileName,
                    targetFileNames
                )

                decryptFile(
                    fileData = fileData,
                    targetFileName = targetFileName
                )

                targetFileNames.add(targetFileName)
            }
        }

        fun decryptDirectory(
            directoryData: AESData,
            targetDirectoryName: String,
            create: Boolean,
            recursion: () -> Unit,
        ) {

            onChild(directoryData.fileName){

                target.onChild(targetDirectoryName) {

                    if (create)
                        target.file.mkdir()

                    recursion()

                    if(!copy)
                        file.delete()
                }
            }

            if (!copy)
                onDirectoryKeyFolder {
                    AESDirectoryKeyDirectory.getAESMetaFile(this, directoryData.fileName)
                        .delete()
                }
        }

        fun decryptUniqueDirectory(
            directoryData: AESData,
            recursion: () -> Unit,
        ) = decryptDirectory(
            directoryData = directoryData,
            targetDirectoryName = directoryData.meta.realName,
            create = true,
            recursion = recursion
        )

        fun emptyDirectoryRecursion() {
            aesContent.aes.files.forEach{
                decryptUniqueFile(
                    fileData = it,
                )
            }
            aesContent.aes.directories.forEach{
                decryptUniqueDirectory(
                    directoryData = it,
                    recursion = ::emptyDirectoryRecursion
                )
            }
        }

        fun decryptDirectory(
            directoryData: AESData,
            targetDirectoryFileNames: MutableList<String>,
            recursion: () -> Unit,
        ) {

            if (!targetDirectoryFileNames.contains(directoryData.meta.realName))
                decryptUniqueDirectory(
                    directoryData = directoryData,
                    recursion = ::emptyDirectoryRecursion
                )
            else {

                val targetDirectoryName = if (overwrite)
                    directoryData.meta.realName
                else
                    AlternativeFileName.file(
                    directoryData.fileName,
                    targetDirectoryFileNames
                )

                if (!overwrite)
                    targetDirectoryFileNames.add(targetDirectoryName)

                decryptDirectory(
                    directoryData = directoryData,
                    targetDirectoryName = targetDirectoryName,
                    create = !overwrite,
                    recursion = if (overwrite) recursion else ::emptyDirectoryRecursion
                )
            }
        }

        fun recursion() {

            val targetFileNames = target.getMutableContent()

            aesContent.aes.files.forEach{
                decryptFile(
                    fileData = it,
                    targetFileNames = targetFileNames.files
                )
            }
            aesContent.aes.directories.forEach{
                decryptDirectory(
                    directoryData = it,
                    targetDirectoryFileNames = targetFileNames.directories,
                    recursion = ::recursion
                )
            }
        }

        val targetFileNames = target.getMutableContent()

        selection.files.forEach{
            decryptFile(
                fileData = it,
                targetFileNames = targetFileNames.files
            )
        }
        selection.directories.forEach{
            decryptDirectory(
                directoryData = it,
                targetDirectoryFileNames = targetFileNames.directories,
                recursion = ::recursion
            )
        }
    }
}