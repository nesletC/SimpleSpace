package com.simplespace.android.lib.simple.fs.aes.data

import com.simplespace.android.lib.simple.fs.aes.file.AESData
import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.simple.stringBytes.DefaultStringBytes
import com.simplespace.android.lib.standard.io.file.suffix.suffixAdded
import com.simplespace.android.lib.standard.random.uniqueRandom
import kotlin.random.Random

object AESFileSystem {

    const val DIRECTORY_KEY_FOLDER = "SIMPLE_AES_256_KEYS"

    const val encryptedFileSuffix = "aes"

    fun randomDirectoryName() = DefaultStringBytes.format64.string(Random.nextBytes(16))

    fun randomFileName() = randomDirectoryName().suffixAdded(encryptedFileSuffix)

    fun randomAESData(
        realName: String,
        fileNameBlackList: List<String>,
        isDirectory: Boolean = false,
    ) = AESData(
        fileName = uniqueRandom(
            blackList = fileNameBlackList,
            getRandom = if (isDirectory)
                AESFileSystem::randomDirectoryName
            else
                AESFileSystem::randomFileName
        ),
        meta = AESMeta(
            realName = realName,
            key = SimpleEncryption.randomKey()
        )
    )
}