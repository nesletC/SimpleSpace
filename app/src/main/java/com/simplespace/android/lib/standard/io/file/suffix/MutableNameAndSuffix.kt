package com.simplespace.android.lib.standard.io.file.suffix

interface MutableNameAndSuffix : NameAndSuffix {
    fun changeName(newName: String)
}