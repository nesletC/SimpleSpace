package com.simplespace.android.lib.simple.fs.aes.file

import javax.crypto.spec.SecretKeySpec

class AESDirectoryOverview (
    val names: AESFileOverview,
    val key: SecretKeySpec
)