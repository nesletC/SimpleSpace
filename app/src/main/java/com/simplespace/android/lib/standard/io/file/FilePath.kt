package com.simplespace.android.lib.standard.io.file


object FilePath {

    const val SEPARATOR = "/"

    fun addNullablePath(root: String, path: String) : String {

        return if (path.isBlank())
            root
        else
            root + SEPARATOR + path
    }
}

