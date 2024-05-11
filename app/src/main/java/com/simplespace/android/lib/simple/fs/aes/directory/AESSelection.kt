package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile

class AESSelection (
    val file: SimpleFile,
    val meta: AESMeta,
    val selection: AESContent
)