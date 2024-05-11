package com.simplespace.android.spaces.model

import com.simplespace.android.lib.simple.fs.aes.data.AESFileSystem
import com.simplespace.android.lib.simple.security.SimpleEncryption
import com.simplespace.android.lib.simple.security.SimpleEncryption.getSecretKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import javax.crypto.spec.SecretKeySpec

@Serializable
data class SpaceDirectoryAccess (
    val fileName: String = AESFileSystem.randomDirectoryName(),
    val key: ByteArray = SimpleEncryption.randomKey()
) {
    @Transient
    private var _secretKey: SecretKeySpec? = null

    val secretKey get() = _secretKey?:key.getSecretKey().also {
        _secretKey = it
    }
}