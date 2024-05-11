package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.characters.SimpleCharSet64
import com.simplespace.android.lib.simple.fs.aes.directory.AESContentNavigation
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectory.decryptRecursively
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectory.encryptRecursively
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectory.moveRecursively
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.getAESMetaFile
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.saveMeta
import com.simplespace.android.lib.simple.fs.aes.directory.AESSelection
import com.simplespace.android.lib.simple.fs.aes.directory.aesContentNavigationOf
import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.moveRecursively
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.onChild
import com.simplespace.android.lib.simple.security.SimpleEncryption.decrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.encrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.getSecretKey
import com.simplespace.android.lib.standard.io.file.suffix.hasSuffix
import com.simplespace.android.lib.standard.random.uniqueRandom
import com.simplespace.android.lib.standard.string.randomChars

class AESFileManager : AESContentNavigation() {

    var detailsState: AESFileManagerFileDetails? = null
    var selectionState: AESSelection? = null
    var temporarySelectionState: AESFileManagerSelectionIndices? = null
    private var minLevel = -1
    var isInRootDirectoryState = false
    var isNothingState = true

    operator fun invoke(
        event: AESFileManagerEvent
    ) : AESFileManagerResult {

        return when (event) {

            AESFileManagerEvent.OpenNothing -> {

                openNothing()

                AESFileManagerResult(
                    isNothingOpenedUpdated = updateNothingOpened(true),
                    isInRootDirectoryUpdated = updateIsInRootDirectory(true),
                    directoryUpdated = true,
                )
            }

            is AESFileManagerEvent.OpenRoot -> {

                aesOpen(
                    file = event.file,
                    meta = event.meta
                )

                minLevel = getLevel()

                AESFileManagerResult(
                    isNothingOpenedUpdated = updateNothingOpened(false),
                    isInRootDirectoryUpdated = updateIsInRootDirectory(true),
                    directoryUpdated = true,
                    activeKeyUsed = true,
                    temporarySelectionUpdated = updateTemporarySelection()
                )
            }


            is AESFileManagerEvent.OpenChild -> {

                aesChild(event.index)

                AESFileManagerResult(
                    isInRootDirectoryUpdated = updateIsInRootDirectory(false),
                    directoryUpdated = true,
                    activeKeyUsed = true,
                    temporarySelectionUpdated = updateTemporarySelection()
                )
            }

            AESFileManagerEvent.OpenParent -> {

                if (!isInRootDirectoryState) {

                    aesParent()

                    val isInRootDirectory = getLevel() == minLevel

                    AESFileManagerResult(
                        isInRootDirectoryUpdated = updateIsInRootDirectory(isInRootDirectory),
                        directoryUpdated = true,
                        activeKeyUsed = true,
                        temporarySelectionUpdated = updateTemporarySelection()
                    )
                }
                else
                    AESFileManagerResult()
            }

            AESFileManagerEvent.DeleteDetails -> {

                detailsState = null

                AESFileManagerResult(
                    detailsUpdated = true
                )
            }


            is AESFileManagerEvent.EncryptedFile -> {

                val fileData = (
                        if (event.isDirectory)
                            aesContent.aes.directories
                        else
                            aesContent.aes.files
                        )[event.index]

                when (event) {

                    is AESFileManagerEvent.EncryptedFile.Rename -> {

                        if (
                            event.isDirectory && event.newName.hasSuffix() ||
                            !event.isDirectory && !event.newName.hasSuffix() ||
                            (content.files + content.directories).contains(event.newName)
                        )
                            AESFileManagerResult(
                                exception = AESFileManagerException.EncryptedFile.Name
                            )
                        else {

                            onDirectoryKeyFolder {

                                getAESMetaFile(
                                    directoryKeyDirectory = this,
                                    sourceFileName = fileData.fileName
                                ).saveMeta(
                                    meta = AESMeta(
                                        realName = event.newName,
                                        key = fileData.meta.key
                                    ),
                                    parentKey = key
                                )
                            }

                            AESFileManagerResult(
                                directoryUpdated = true,
                                activeKeyUsed = true,
                                exception = null
                            )
                        }
                    }

                    is AESFileManagerEvent.EncryptedFile.ChangeKey -> {

                        val newKey = event.newKey.getSecretKey()

                        if (event.isDirectory) {

                            aesChild(fileData)

                            onDirectoryKeyFolder {

                                aesContent.aes.files.forEach{
                                    getAESMetaFile(this, it.fileName).saveMeta(
                                        it.meta, newKey
                                    )
                                }

                                aesContent.aes.directories.forEach{
                                    getAESMetaFile(this, it.fileName).saveMeta(
                                        it.meta, newKey
                                    )
                                }
                            }

                            aesParent()
                        }
                        else{

                            onChild(fileData.fileName) {

                                val file = file

                                changeName(
                                    uniqueRandom(
                                        blackList = content.files,
                                        getRandom = {
                                            SimpleCharSet64.default.randomChars(16) +
                                                ".tmp"
                                        }
                                    )
                                )

                                val tempFile = file

                                file.decrypt(fileData.meta.secretKey, tempFile)

                                tempFile.encrypt(newKey, file)
                            }
                        }

                        onDirectoryKeyFolder {

                            getAESMetaFile(this, fileData.fileName).saveMeta(
                                AESMeta(
                                    realName = fileData.meta.realName,
                                    key = event.newKey
                                ),
                                key
                            )
                        }

                        updateAESContent()

                        AESFileManagerResult(
                            directoryUpdated = true,
                            activeKeyUsed = true
                        )
                    }

                    is AESFileManagerEvent.EncryptedFile.ChangeFileName -> {

                        if (
                            event.isDirectory == event.newName.hasSuffix() ||
                            (event.isDirectory && content.directories.contains(event.newName)) ||
                            (!event.isDirectory && content.files.contains(event.newName))
                        )
                            AESFileManagerResult(
                                exception = AESFileManagerException.EncryptedFile.FileName
                            )
                        else {

                            onChild(fileData.fileName) {

                                val oldFile = file

                                changeName(event.newName)

                                oldFile.renameTo(file)
                            }

                            onDirectoryKeyFolder {
                                getAESMetaFile(this, event.newName).saveMeta(
                                    fileData.meta, key
                                )
                                getAESMetaFile(this, fileData.fileName).delete()
                            }

                            AESFileManagerResult(
                                directoryUpdated = true,
                                activeKeyUsed = true,
                            )
                        }
                    }

                    is AESFileManagerEvent.EncryptedFile.ShowDetails -> {

                        detailsState = AESFileManagerFileDetails.AES(
                            isDirectory = event.isDirectory,
                            data = fileData
                        )

                        AESFileManagerResult(
                            detailsUpdated = true,
                        )
                    }
                }
            }


            is AESFileManagerEvent.File ->

                when (event) {

                    is AESFileManagerEvent.File.Rename -> {

                        if (event.isDirectory == event.newName.hasSuffix() ||
                            (event.isDirectory && content.directories.contains(event.newName)) ||
                            (!event.isDirectory && content.files.contains(event.newName))
                        )
                            AESFileManagerResult(
                                exception = AESFileManagerException.File.Rename
                            )

                        else {
                            onChild(
                                (
                                    if (event.isDirectory)
                                        aesContent.raw.directories
                                    else
                                        aesContent.raw.files
                                ) [event.index]
                            ) {
                                val oldFile = file

                                changeName(event.newName)

                                oldFile.renameTo(file)
                            }

                            AESFileManagerResult(
                                directoryUpdated = true,
                            )
                        }
                    }

                    is AESFileManagerEvent.File.ShowDetails -> {
                        detailsState =
                            AESFileManagerFileDetails.Raw(
                                isDirectory = event.isDirectory,
                                name =
                                if (event.isDirectory)
                                    aesContent.raw.directories[event.index]
                                else
                                    aesContent.raw.files[event.index]
                            )

                        AESFileManagerResult(
                            detailsUpdated = true
                        )
                    }
                }

            is AESFileManagerEvent.TemporarySelection -> {

                when (event) {
                    AESFileManagerEvent.TemporarySelection.Init -> {

                        temporarySelectionManager.init(aesContent)
                        temporarySelectionState = temporarySelectionManager.selectionIndices()
                    }

                    AESFileManagerEvent.TemporarySelection.All -> {
                        temporarySelectionManager.selectAll()
                        temporarySelectionState = temporarySelectionManager.selectionIndices()
                    }

                    AESFileManagerEvent.TemporarySelection.None -> {
                        temporarySelectionManager.unSelectAll()
                        temporarySelectionState = temporarySelectionManager.selectionIndices()
                    }

                    is AESFileManagerEvent.TemporarySelection.Switch -> {
                        temporarySelectionManager.switch(event)
                        temporarySelectionState = temporarySelectionManager.selectionIndices()
                    }

                    AESFileManagerEvent.TemporarySelection.Abort -> {
                        temporarySelectionManager.clear()
                        temporarySelectionState = null
                    }
                }

                AESFileManagerResult(
                    temporarySelectionUpdated = true,
                )
            }

            AESFileManagerEvent.CancelingSelection -> {

                selectionState = null

                AESFileManagerResult(
                    selectionUpdated = true,
                )
            }

            is AESFileManagerEvent.SavingSelection -> {

                selectionState = temporarySelectionState!!.selection()

                clearTemporarySelection()

                AESFileManagerResult(
                    selectionUpdated = true,
                    temporarySelectionUpdated = true,
                )
            }

            is AESFileManagerEvent.ExecutingSelectionAction -> {

                val selection = selectionState!!
                val action = event.action

                selectionState = null

                val temporarySelectionUpdated = temporarySelectionState != null

                if (temporarySelectionUpdated) {

                    temporarySelectionState = null
                    temporarySelectionManager.clear()
                }

                when (action) {
                    is AESFileManagerSelectionAction.Move -> {

                        aesContentNavigationOf(
                            file = selection.file,
                            data = selection.meta
                        )
                            .moveRecursively(
                                target = this,
                                overwrite = action.overwrite,
                                copy = action.copy,
                                selection = selection.selection
                            )
                    }

                    is AESFileManagerSelectionAction.Decrypt -> {

                        val source = aesContentNavigationOf(selection.file, selection.meta)

                        source.decryptRecursively(
                            selection = selection.selection,
                            target = this,
                            overwrite = action.overwrite,
                            copy = action.copy,
                        )
                    }

                    AESFileManagerSelectionAction.Delete -> {

                        selection.selection.raw.files.forEach {
                            onChild(it){
                                file.delete()
                            }
                        }

                        selection.selection.raw.directories.forEach {
                            onChild(it) {
                                file.delete()
                            }
                        }

                        val aesFilesToDelete =
                            selection.selection.aes.getFileNames()

                        aesFilesToDelete.files.forEach{
                            onChild(it) {
                                file.delete()
                            }
                        }

                        aesFilesToDelete.directories.forEach{
                            onChild(it) {
                                file.delete()
                            }
                        }

                        aesFilesToDelete.files.forEach{
                            onDirectoryKeyFolder {
                                getAESMetaFile(this, it).delete()
                            }
                        }

                        aesFilesToDelete.directories.forEach{
                            onDirectoryKeyFolder {
                                getAESMetaFile(this, it).delete()
                            }
                        }
                    }

                    is AESFileManagerSelectionAction.Encrypt ->

                        aesContentNavigationOf(
                            file = selection.file,
                            data = selection.meta
                        )
                            .encryptRecursively(
                                selection = selection.selection,
                                target = this,
                                overwrite = action.overwrite,
                                copy = action.copy
                            )
                }

                updateAESContent()

                AESFileManagerResult(
                    directoryUpdated = true,
                    selectionUpdated = true,
                    activeKeyUsed =
                        selection.selection.aes.directories.isNotEmpty() &&
                        selection.selection.aes.files.isNotEmpty(),
                    temporarySelectionUpdated = temporarySelectionUpdated,
                )
            }

            is AESFileManagerEvent.Import -> {

                if (event.encrypt)
                    event.source.encryptRecursively(
                        target = this,
                        overwrite = event.action.overwrite,
                        copy = event.action.copy,
                        selection = event.selection
                    )
                else
                    event.source.moveRecursively(
                        target = this,
                        overwrite = event.action.overwrite,
                        copy = event.action.copy,
                        selection = event.selection
                    )

                AESFileManagerResult(
                    directoryUpdated = true,
                    activeKeyUsed = event.encrypt
                )
            }

            is AESFileManagerEvent.Export -> {

                val selection = temporarySelectionState!!.selection()

                clearTemporarySelection()

                aesContentNavigationOf(
                    selection.file.mutableCopy(), selection.meta
                ).decryptRecursively(
                    target = event.target,
                    copy = event.action.copy,
                    overwrite = event.action.overwrite,
                    selection = selection.selection
                )

                val selectionUpdated = selectionState == null
                if (selectionUpdated)
                    selectionState = null

                AESFileManagerResult(
                    directoryUpdated = !event.action.copy,
                    temporarySelectionUpdated = true,
                    activeKeyUsed = true,
                    selectionUpdated = selectionUpdated,
                )
            }
        }
    }


    // returns true if the state was updated

    private fun updateNothingOpened(newState: Boolean) : Boolean {

        val updating = newState != isNothingState

        if (updating)
            isNothingState = newState

        return updating
    }

    // returns true if the state was updated

    private fun updateIsInRootDirectory(newState: Boolean) : Boolean {

        val updating = newState != isInRootDirectoryState

        if (updating)
            isInRootDirectoryState = newState

        return updating
    }

    // returns true if the state was updated

    private fun updateTemporarySelection() : Boolean {

        val currentSelectionState = selectionState

        val currentTemporarySelection = temporarySelectionState

        val isSelectionInCurrentDirectory =
            currentSelectionState?.file?.equalsFile(this) ?:false

        val update = isSelectionInCurrentDirectory != (currentTemporarySelection == null)

        if (update) {

            val contentDataState = aesContent

            temporarySelectionState = if (isSelectionInCurrentDirectory) {

                temporarySelectionManager.init(contentDataState)

                temporarySelectionManager.select(

                    contentDataState.getIndices(
                        currentSelectionState!!.selection.getFileNames()
                    )
                )

                temporarySelectionManager.selectionIndices()
            }
            else {

                temporarySelectionManager.clear()

                null
            }
        }

        return update
    }

    private fun clearTemporarySelection() {

        temporarySelectionManager.clear()
        temporarySelectionState = null
    }

    private fun AESFileManagerSelectionIndices.selection() = AESSelection(
        file = copy(),
        meta = meta,
        selection = aesContent.selection(
            getIndices()
        )
    )

    private val temporarySelectionManager = AESFileManagerSelectionIndicesManager()
}