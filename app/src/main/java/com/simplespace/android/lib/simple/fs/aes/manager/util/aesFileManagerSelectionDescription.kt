package com.simplespace.android.lib.simple.fs.aes.manager.util

import com.simplespace.android.lib.simple.fs.aes.directory.AESContent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionDescription

fun aesFileManagerSelectionDescription (
    selection: AESContent
) = AESFileManagerSelectionDescription(
    filesCount = selection.aes.files.size + selection.raw.files.size,
    directoriesCount = selection.aes.directories.size + selection.raw.directories.size,
)