package com.simplespace.android.lib.simple.fs.aes.file

import com.simplespace.android.lib.simple.security.SimpleEncryption.getSecretKey
import javax.crypto.spec.SecretKeySpec

class AESMeta(
    val realName: String,
    val key: ByteArray,
    val secretKey : SecretKeySpec = key.getSecretKey()
)