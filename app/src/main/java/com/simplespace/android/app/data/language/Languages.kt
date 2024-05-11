package com.simplespace.android.app.data.language

import com.simplespace.android.lib.standard.iterable.list.getIndicesMap

object Languages {

    val entries = Language.entries

    val index = entries.getIndicesMap()

    fun code(language: Language) = when (language) {
        Language.ENGLISH -> "EN"
        Language.GERMAN -> "DE"
    }
}