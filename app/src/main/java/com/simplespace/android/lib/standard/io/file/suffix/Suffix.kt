package com.simplespace.android.lib.standard.io.file.suffix

import com.simplespace.android.lib.standard.io.file.FileType
import com.simplespace.android.lib.standard.io.file.FileTypes

fun String.hasSuffix() : Boolean = lastSuffixSeparatorIndex() != -1

fun mutableNameAndSuffix(name: String, suffix: String) : MutableNameAndSuffix =
    MutableNameAndSuffixImplementation(name, suffix)

fun nameAndSuffix(name: String, fileType: FileType) : NameAndSuffix =
    NameAndSuffixImplementation(name, fileType.name)

fun nameAndSuffix(name: String, suffix: String) : NameAndSuffix =
    NameAndSuffixImplementation(name, suffix)

fun String.toNameAndSuffix(fileType: FileType)  = nameAndSuffix(this, fileType)

fun String.toNameAndSuffix(suffix: String) = nameAndSuffix(this, suffix)

fun String.toNameAndSuffix() : NameAndSuffix = toMutableNameAndSuffix()

fun String.toMutableNameAndSuffix() : MutableNameAndSuffix {

    val suffixSeparatorIndex = lastSuffixSeparatorIndex()

    return mutableNameAndSuffix(
        name = substring(0, suffixSeparatorIndex),
        suffix = substring(suffixSeparatorIndex + 1)
    )
}

fun String.suffixAdded(fileType: FileType) = suffixAdded(FileTypes.name(fileType))

fun String.suffixAdded(suffix: String) = "$this$SUFFIX_SEPARATOR$suffix"

fun String.suffixRemoved() = substring(0, lastSuffixSeparatorIndex())

const val SUFFIX_SEPARATOR = "."

private data class MutableNameAndSuffixImplementation(
    override var name: String,
    override val suffix: String
) : MutableNameAndSuffix, NameAndSuffixBase() {

    override fun changeName(newName: String) {
        name = newName
        joinedValue = null
    }
}

private data class NameAndSuffixImplementation(
    override val name: String,
    override val suffix: String,
) : NameAndSuffix, NameAndSuffixBase()

private abstract class NameAndSuffixBase : NameAndSuffix {

    override val joined get() = joinedValue?:name.suffixAdded(suffix).also {
        joinedValue = it
    }

    var joinedValue: String? = null
}

private fun String.lastSuffixSeparatorIndex() = lastIndexOf(SUFFIX_SEPARATOR)