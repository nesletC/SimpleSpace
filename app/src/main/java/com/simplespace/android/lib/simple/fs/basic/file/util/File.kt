package com.simplespace.android.lib.simple.fs.basic.file.util

import com.simplespace.android.lib.simple.fs.basic.directory.simpleMutableFileOf
import com.simplespace.android.lib.simple.fs.basic.file.simpleFileOf
import java.io.File

fun File.simple() = simpleFileOf(absolutePath)

fun File.simpleMutable() = simpleMutableFileOf(absolutePath)