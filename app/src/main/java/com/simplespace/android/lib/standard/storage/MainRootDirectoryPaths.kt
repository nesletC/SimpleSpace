package com.simplespace.android.lib.standard.storage

import android.content.Context

class MainRootDirectoryPaths(context: Context) {
    val protected : String = context.filesDir.absolutePath
    val app = context.getExternalFilesDir(null)?.absolutePath
}