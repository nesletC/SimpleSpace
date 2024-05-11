package com.simplespace.android.spaces.data

import com.simplespace.android.app.App
import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.onChild
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.security.SimpleEncryption.decrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.encrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.getSecretKey
import com.simplespace.android.lib.standard.chain.reader.read
import com.simplespace.android.lib.standard.chain.writer.write
import com.simplespace.android.lib.standard.io.file.getDirectoryContent
import com.simplespace.android.lib.standard.iterable.list.getAll
import com.simplespace.android.lib.standard.storage.Storage
import com.simplespace.android.lib.standard.tryFunctions.tryBoolean
import com.simplespace.android.lib.standard.tryFunctions.tryNull
import com.simplespace.android.spaces.model.SpaceAccess
import com.simplespace.android.spaces.model.SpaceMeta
import com.simplespace.android.spaces.model.SpaceMetaSaveable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.crypto.spec.SecretKeySpec

object SpaceAuthentication {

    sealed class Error  {

        data object AuthenticationFailed : Error()

        sealed class IO (
            val directoryStorage: Storage
        ): Error() {

            class Creation(
                directoryStorage: Storage
            ) : IO(directoryStorage)

            class Deletion(
                directoryStorage: Storage
            ) : IO(directoryStorage)

            class Rename(
                directoryStorage: Storage
            ) : IO(directoryStorage)

            class Key(
                directoryStorage: Storage
            ) : IO(directoryStorage)
        }
    }

    sealed class Action {

        data object Load: Action()

        class Delete(
            val index: Int,
        ) : Action()

        sealed class Save ( val metaToSave: SpaceMeta ): Action() {

            class New( newMeta: SpaceMeta ) : Save(newMeta)

            class Update(

                // index required to update the session
                val index: Int,

                updatedMeta: SpaceMeta,

                ) : Save( updatedMeta )
        }
    }

    fun load(key: SpaceAccess) : Error.AuthenticationFailed? {

        val file = key.file()

        val error = Error.AuthenticationFailed

        if (!file.exists())
            return error

        return SpaceSessions.start(
            file.loadSpaceMeta(key.key)
                ?:return error
        )?.let{
            error
        }
    }

    fun save(spaceAccess: SpaceAccess, meta: SpaceMeta ) : Error? {

        val file = spaceAccess.file()

        if (file.exists())
            return Error.AuthenticationFailed

        val protectedDirectory = meta.protectedDirectoryFile()

        if (protectedDirectory.file.exists())
            return Error.IO.Creation(Storage.Main.Protected)

        protectedDirectory.makeSpaceDirectory()

        val metaFile = spaceAccess.file()

        edit(
            originalMeta = meta.copy(
                appDirectory = null,
                directories = listOf()
            ),
            updatedMeta = meta
        )?.let {

            metaFile.saveSpaceMeta(spaceAccess.key, it.second)

            SpaceSessions.start(it.second)

            return it.first
        }

        metaFile.saveSpaceMeta(spaceAccess.key, meta)

        SpaceSessions.start(meta)

        return null
    }

    fun edit (
        key: SpaceAccess,
        index: Int,
        meta: SpaceMeta
    ) : Error? {

        val metaFile = key.file()

        if (!metaFile.exists())
            return Error.AuthenticationFailed

        val originalMeta = metaFile.loadSpaceMeta(key.key)?:return Error.AuthenticationFailed

        if (
            originalMeta.protectedDirectory.fileName !=
            SpaceSessions.getMeta(index)?.protectedDirectory?.fileName
        )
            return Error.AuthenticationFailed

        SpaceSessions.stop(index)

        edit(
            originalMeta = originalMeta,
            updatedMeta = meta,
        )?.let {

            metaFile.saveSpaceMeta(key.key, it.second)

            SpaceSessions.start( it.second )

            return it.first
        }

        metaFile.saveSpaceMeta(key.key, meta)

        SpaceSessions.start( meta )

        return null
    }


    fun delete(
        spaceAccess: SpaceAccess,
        index: Int,
    ) : Error? {

        val meta = SpaceSessions.getMeta(index)?:return Error.AuthenticationFailed

        val directoryFileName = meta.protectedDirectory.fileName

        val metaFile = spaceAccess.file()

        if (!metaFile.exists())
            return Error.AuthenticationFailed

        val fileMeta =
            metaFile.loadSpaceMeta(spaceAccess.key)?:return Error.AuthenticationFailed

        if (fileMeta.protectedDirectory.fileName != directoryFileName)
            return Error.AuthenticationFailed


        SpaceSessions.stop(index)


        // delete all directories

        edit(
            originalMeta = meta,
            updatedMeta =
                meta.copy(
                    directories = listOf(),
                    appDirectory = null
                )
        )
            ?.let {
                metaFile.saveSpaceMeta(spaceAccess.key, it.second)

                SpaceSessions.start(it.second)

                return it.first
            }

        SpaceStorages.getProtectedDirectory(directoryFileName).file.deleteRecursively()

        metaFile.delete()

        return null
    }

    private fun edit(
        originalMeta: SpaceMeta,
        updatedMeta: SpaceMeta,
    ) : Pair<Error, SpaceMeta>? {

        val protectedDirectory = originalMeta.protectedDirectoryFile()

        val newInternalDirName = updatedMeta.protectedDirectory.fileName

        if (newInternalDirName != protectedDirectory.name) {

            protectedDirectory.changeName(newInternalDirName)

            if (protectedDirectory.file.exists())
                return Pair(
                    Error.IO.Creation(Storage.Main.Protected),
                    originalMeta
                )

            protectedDirectory.file.renameTo(protectedDirectory.file)
        }

        val newInternalDirectoryKey = updatedMeta.protectedDirectory.key
        val oldInternalDirectoryKey = originalMeta.protectedDirectory.key

        if (!newInternalDirectoryKey.contentEquals(oldInternalDirectoryKey)) {

            protectedDirectory.changeSpaceDirectoryKey(
                original = oldInternalDirectoryKey.getSecretKey(),
                new = newInternalDirectoryKey.getSecretKey(),
            )
        }

        val newAppDirectoryFileName = updatedMeta.appDirectory?.fileName
        val oldAppDirectoryFileName = originalMeta.appDirectory?.fileName

        var appDirectoryError: Error? = null

        var appDirectory: SimpleMutableFile? = null

        var isOldAndNewAppDirectoryNotNull = false

        if (newAppDirectoryFileName == null && oldAppDirectoryFileName != null) {

            // delete

            appDirectory = originalMeta.appDirectoryFile()

            if (appDirectory == null)

                appDirectoryError = Error.IO.Deletion(Storage.Main.Open.App)

            else
                appDirectory.file.deleteRecursively()
        }
        else if (oldAppDirectoryFileName == null && newAppDirectoryFileName != null) {

            // create

            appDirectory = updatedMeta.appDirectoryFile()

            if (appDirectory == null || appDirectory.file.exists())

                appDirectoryError = Error.IO.Creation(Storage.Main.Open.App)

            else {
                appDirectory.makeSpaceDirectory()
            }
        }
        else if (oldAppDirectoryFileName != null) {

            isOldAndNewAppDirectoryNotNull = true

            appDirectory = originalMeta.appDirectoryFile()

            if (oldAppDirectoryFileName != newAppDirectoryFileName) {

                // rename

                val originalAppDirectoryFile = appDirectory?.file

                appDirectory?.changeName(newAppDirectoryFileName!!)

                if (
                    appDirectory == null ||
                    appDirectory.file.exists() ||
                    originalAppDirectoryFile?.renameTo(appDirectory.file) != true
                )
                    appDirectoryError = Error.IO.Rename(Storage.Main.Open.Public)
            }
        }

        appDirectoryError?.let {

            return Pair(
                it,
                updatedMeta.copy(
                    appDirectory = originalMeta.appDirectory,
                    directories = originalMeta.directories
                )
            )
        }

        if (isOldAndNewAppDirectoryNotNull) {

            val oldAppDirectoryKey = originalMeta.appDirectory!!.key
            val newAppDirectoryKey = updatedMeta.appDirectory!!.key

            if (!oldAppDirectoryKey.contentEquals(newAppDirectoryKey)) {

                // change key

                if (appDirectory == null)
                    return Pair(
                        Error.IO.Key(Storage.Main.Open.App),
                        updatedMeta.copy(
                            appDirectory = updatedMeta.appDirectory.copy(
                                key = oldAppDirectoryKey
                            ),
                            directories = originalMeta.directories
                        )
                    )

                appDirectory.changeSpaceDirectoryKey(
                    original = originalMeta.appDirectory.secretKey,
                    new = updatedMeta.appDirectory.secretKey
                )
            }
        }


        // Custom directories -------------------------------------------------------------------

        val originalDirectories =
            mutableListOf<SimpleMutableFile>()

        originalMeta.directories.forEach{

            originalDirectories.add(it.mutableFile())
        }

        val directoriesToCreate =
            mutableMapOf<Int, SimpleMutableFile>()

        updatedMeta.directories.forEachIndexed{ index, spaceDirectory ->

            directoriesToCreate[index] = spaceDirectory.mutableFile()
        }

        val originalDirectoriesToUpdate = mutableListOf<Int>()
        val directoriesToUpdate = mutableListOf<Int>()

        val deletedDirectories = mutableListOf<Int>()

        val createdDirectories = mutableListOf<Int>()

        originalDirectories.forEachIndexed{ originalIndex, originalFile ->

            var isToDelete = true

            run directoriesToCreate@ {

                directoriesToCreate.forEach{

                    if (originalFile.equalsFile(it.value)) {

                        directoriesToCreate.remove(it.key)

                        originalDirectoriesToUpdate.add(originalIndex)
                        directoriesToUpdate.add(it.key)

                        isToDelete = false

                        return@directoriesToCreate
                    }
                }
            }

            if (
                isToDelete
            ) {

                if (
                    !tryBoolean {
                        originalFile.file.deleteRecursively()
                    }
                ) {

                    return Pair(
                        Error.IO.Deletion(Storage.Custom(
                            originalMeta.directories[originalIndex].rootDirectoryIndex
                        )),
                        updatedMeta.copy(
                            directories = originalMeta.directories.run{
                                getAll(originalDirectoriesToUpdate) + subList(originalIndex, size)
                            }
                        )
                    )
                }

                deletedDirectories.add(originalIndex)
            }
        }

        directoriesToCreate.forEach{

            if (!it.value.file.exists()) {

                it.value.makeSpaceDirectory()

                createdDirectories.add(it.key)
            }

            else return Pair(
                Error.IO.Creation(
                    Storage.Custom(
                        updatedMeta.directories[it.key].rootDirectoryIndex
                    )
                ),
                updatedMeta.copy(
                    directories =
                        updatedMeta.directories.run {
                            getAll(directoriesToUpdate) + getAll(createdDirectories)
                        }
                )
            )
        }

        originalDirectoriesToUpdate.forEachIndexed{ index, originalDirectoriesIndex ->

            val updatedDirectoriesIndex = directoriesToUpdate[index]

            val originalSpaceDirectory =
                originalMeta.directories[originalDirectoriesIndex]

            originalDirectories[originalDirectoriesIndex].changeSpaceDirectoryKey(
                original = originalSpaceDirectory.access.secretKey,
                new = updatedMeta.directories[updatedDirectoriesIndex].access.secretKey
            )
        }

        return null
    }

    private fun SimpleMutableFile.changeSpaceDirectoryKey(
        original: SecretKeySpec,
        new: SecretKeySpec
    ) {

        child(AESFileSystem.DIRECTORY_KEY_FOLDER)

        val filesToEdit = file.getDirectoryContent().files

        filesToEdit.forEach {

            child(it)
            val file = file
            parent()

            val content = file.read {
                decrypt(original)
            }
            file.write {
                encrypt(key = new, bytesToEncrypt = content)
            }
        }

        parent()
    }

    private fun File.saveSpaceMeta(key: SecretKeySpec, meta: SpaceMeta ) {
        write {

            encrypt(
                key = key,
                bytesToEncrypt =
                    Json.encodeToString(
                        meta.saveable()
                    ).encodeToByteArray()
            )
        }
    }

    private fun File.loadSpaceMeta(key: SecretKeySpec) : SpaceMeta? =

        tryNull {

            Json.decodeFromString<SpaceMetaSaveable>(
                read{
                    decrypt(key)
                }.decodeToString()
            )
                .meta()
        }

    private fun SpaceAccess.file() : File {

        configFolder.child(fileName)

        val file = configFolder.file

        configFolder.parent()

        return file
    }

    private fun SimpleMutableFile.makeSpaceDirectory() {

        file.mkdir()

        onChild(AESFileSystem.DIRECTORY_KEY_FOLDER) {
            file.mkdir()
        }
    }

    private var configFolder : SimpleMutableFile = App.mainRootDirectories.protected()

    private val configFiles = mutableListOf<String>()

    private const val configFolderName = "simple_aes_config"

    init {

        configFolder.child(configFolderName)

        if (!configFolder.file.exists())
            configFolder.file.mkdir()

        configFiles.addAll(

            configFolder.file.getDirectoryContent().files
        )
    }
}