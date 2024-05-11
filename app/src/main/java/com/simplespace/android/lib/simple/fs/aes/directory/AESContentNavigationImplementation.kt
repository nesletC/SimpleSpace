package com.simplespace.android.lib.simple.fs.aes.directory

import com.simplespace.android.lib.simple.fs.aes.file.AESMeta
import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile

fun aesContentNavigationOf(
    file: SimpleFile,
    data: AESMeta,
) : AESContentNavigation =
    AESContentNavigationImplementation(file, data)

private class AESContentNavigationImplementation(
    file: SimpleFile,
    data: AESMeta,
) : AESContentNavigation() {

    init{
        aesOpen(file, data)
    }
}