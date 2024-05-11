package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem.DIRECTORY_KEY_FOLDER
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.loadAESDirectoryContent
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.loadMeta
import com.simplespace.android.lib.simple.fs.aes.directory.AESDirectoryKeyDirectory.saveMeta
import com.simplespace.android.lib.simple.fs.aes.file.AESData
import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.onChild
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleFileMutableBase
import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.standard.storage.Storages
import kotlin.properties.Delegates

abstract class AESContentNavigation : SimpleFileMutableBase(Storages.indexOf("")) {

    private var _aesContent : AESContent? = null

    val aesContent get() = _aesContent?:getUpdatedAESContent()

    var meta by Delegates.notNull<AESMeta>()
    private set

    val key get() = meta.secretKey

    fun openNothing() {

        openRoot("")

        _aesContent = AESContent()

        meta = AESMeta("", SimpleEncryption.randomKey())
    }

    fun aesOpen(
        file: SimpleFile,
        meta: AESMeta,
    ) {
        metaBackStack.clear()
        open(file)

        onNavigate(meta)
    }

    fun aesChild(index: Int) = aesChild(aesContent.aes.directories[index])

    fun aesChild(data: AESData) {

        metaBackStack.add(meta)
        child(data.fileName)

        onNavigate(data.meta)
    }

    fun aesParent() {

        parent()

        onNavigate(metaBackStack.removeLast())
    }

    fun updateAESContent() {

        _aesContent = aesContentNavigationOf(
            file = mutableCopy(),
            data = meta
        ).let{ copy ->

            copy.onDirectoryKeyFolder {

                loadAESDirectoryContent(
                    directoryKeyDirectory = copy,
                    key = meta.secretKey,
                    contentDirectories = content.directories,
                    contentFiles = content.files,
                    accessFileNames = copy.file.list()!!
                ).also { _aesContent = it }
            }
        }
    }

    fun getUpdatedAESContent() : AESContent {
        updateAESContent()
        return aesContent
    }

    inline fun <T>onDirectoryKeyFolder(action: () -> T) = onChild(DIRECTORY_KEY_FOLDER, action)

    fun saveChildMeta(
        fileName: String, meta: AESMeta
    ) {

        onDirectoryKeyFolder {

            AESDirectoryKeyDirectory.getAESMetaFile(this, fileName)
                .saveMeta(meta, key)
        }
    }

    fun saveChildMeta(
        data: AESData
    ) = saveChildMeta(
        fileName = data.fileName,
        meta = data.meta
    )

    fun getChildMeta(
        fileName: String,
    ) {
        onDirectoryKeyFolder {
            AESDirectoryKeyDirectory.getAESMetaFile(this, fileName)
                .loadMeta(key)
        }
    }

    private fun onNavigate(newMeta: AESMeta) {
        meta = newMeta
        _aesContent = null
    }

    private val metaBackStack = mutableListOf<AESMeta>()
}