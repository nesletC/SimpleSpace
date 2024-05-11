package com.simplespace.android.spaces.model

import javax.crypto.spec.SecretKeySpec

class SpaceAccess(
    val fileName: String,
    val key: SecretKeySpec,
)