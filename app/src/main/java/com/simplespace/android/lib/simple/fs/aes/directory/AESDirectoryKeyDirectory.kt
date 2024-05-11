package com.simplespace.android.lib.simple.fs.aes.directory

import android.util.Log
import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.fs.aes.file.AESData
import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleMutableFile
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.simple.security.SimpleEncryption.decrypt
import com.simplespace.android.lib.simple.security.SimpleEncryption.encrypt
import com.simplespace.android.lib.standard.chain.reader.chainReader
import com.simplespace.android.lib.standard.chain.reader.read
import com.simplespace.android.lib.standard.chain.writer.write
import com.simplespace.android.lib.standard.io.file.fileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.suffix.suffixAdded
import com.simplespace.android.lib.standard.io.file.suffix.suffixRemoved
import java.io.File
import javax.crypto.spec.SecretKeySpec

object AESDirectoryKeyDirectory {

    fun loadAESDirectoryContent(
        directoryKeyDirectory: SimpleMutableFile,
        key: SecretKeySpec,
        contentDirectories: MutableList<String>,
        contentFiles: MutableList<String>,
        accessFileNames: Array<String>,
    ) =
        directoryKeyDirectory.loadAESDirectoryContentFromKeyDirectory(
            key, contentDirectories, contentFiles, accessFileNames
        )

    fun getAESMetaFile(
        directoryKeyDirectory: SimpleMutableFile,
        sourceFileName: String
    ) : File =
        directoryKeyDirectory.getAESMetaFileFromKeyDirectory(sourceFileName)


    fun File.loadMeta(
        targetParentKey: SecretKeySpec
    ) : AESMeta =

        read{

            val content = decrypt(targetParentKey).chainReader()

            val meta = AESMeta(
                key = content.read(SimpleEncryption.KEY_SIZE),
                realName = content.read(256).decodeToString()
            )

            Log.d("asdf", "loaded meta for ${meta.realName}")

            meta
        }


    fun File.saveMeta(
        meta: AESMeta,
        parentKey: SecretKeySpec,
    ) {

        write{

            encrypt(key = parentKey, bytesToEncrypt = meta.key + meta.realName.encodeToByteArray())
        }
    }


    private fun SimpleMutableFile.loadAESDirectoryContentFromKeyDirectory(
        key: SecretKeySpec,
        contentDirectories: MutableList<String>,
        contentFiles: MutableList<String>,
        accessFileNames: Array<String>,
    ) : AESContent {

        val aesDirectories = mutableListOf<AESData>()
        val aesFiles = mutableListOf<AESData>()

        contentDirectories.remove(AESFileSystem.DIRECTORY_KEY_FOLDER)

        accessFileNames.forEach {

            val fileName = it.suffixRemoved()

            val contentDirectoryIndex = contentDirectories.indexOf(fileName)
            val contentFileIndex = contentFiles.indexOf(fileName)

            if (contentDirectoryIndex != -1) {

                contentDirectories.removeAt(contentDirectoryIndex)

                child(it)

                aesDirectories.add(
                    AESData(
                        fileName = fileName,
                        meta = file.loadMeta(key),
                    )
                )

                parent()
            }
            else if (contentFileIndex != -1) {

                contentFiles.removeAt(contentFileIndex)

                child(it)

                aesFiles.add(
                    AESData(
                        fileName = fileName,
                        meta = file.loadMeta(key)
                    )
                )

                parent()
            }
        }

        return AESContent(
            raw = fileNamesWithDirectoryPartition(
                directories = contentDirectories,
                files = contentFiles,
            ),
            aes = AESContentDataEncrypted(
                directories = aesDirectories,
                files = aesFiles
            )
        )
    }

    private fun SimpleMutableFile.getAESMetaFileFromKeyDirectory(
        sourceFileName: String,
    ) : File {
        child(
            sourceFileName.suffixAdded(AESFileSystem.encryptedFileSuffix)
        )
        val file = file
        parent()
        return file
    }
}