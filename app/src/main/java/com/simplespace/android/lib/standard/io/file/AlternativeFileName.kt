package com.simplespace.android.lib.standard.io.file

import com.simplespace.android.lib.standard.io.file.suffix.MutableNameAndSuffix
import com.simplespace.android.lib.standard.io.file.suffix.toMutableNameAndSuffix

object AlternativeFileName {

    fun directory(directoryName: String, blackList: List<String>) : String = makeUnique(
        fileName = directoryName
    ) {
        blackList.contains(it)
    }

    fun file(fileName: String, blackList: List<String>) : String {

        val mutable = fileName.toMutableNameAndSuffix()

        makeFileUnique(mutable, blackList)

        return mutable.joined
    }

    private fun makeFileUnique(
        fileName: MutableNameAndSuffix, blackList: List<String>
    ) {
        makeUnique(
            fileName.name
        ) {
            fileName.changeName(it)
            blackList.contains(fileName.joined)
        }
    }

    private inline fun makeUnique(
        fileName: String,
        isNotUnique: (String) -> Boolean
    ) : String {

        var index = 1
        var result = fileName.alternate(index)

        while(isNotUnique(result)) {

            index++
            result = fileName.alternate(index)
        }

        return result
    }

    private fun String.alternate(index: Int) = plus(ALTERNATIVE_FILE_NAME_SEPARATOR) + index

    private const val ALTERNATIVE_FILE_NAME_SEPARATOR = "-"
}