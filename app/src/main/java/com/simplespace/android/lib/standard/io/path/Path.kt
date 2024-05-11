package com.simplespace.android.lib.standard.io.path

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

fun Path.move(target: Path, overwrite: Boolean) {
    if (overwrite)
        Files.move(this, target, StandardCopyOption.REPLACE_EXISTING)
    else
        Files.move(this, target)
}