package com.simplespace.android.lib.simple.fs.aes.file

class AESData(
    val fileName: String,
    val meta: AESMeta,
) {
    fun fileOverview() = AESFileOverview(
        fileName = fileName,
        realName = meta.realName
    )
}