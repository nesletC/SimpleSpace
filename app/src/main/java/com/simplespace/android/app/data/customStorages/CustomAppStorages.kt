package com.simplespace.android.app.data.customStorages

import android.os.Environment
import com.simplespace.android.lib.standard.storage.CustomStorageData
import com.simplespace.android.lib.standard.storage.CustomStorages

object CustomAppStorages : CustomStorages<CustomAppStorage>() {

    init{
        register(
            CustomAppStorage.PUBLIC, CustomStorageData(
                label = "Internal Storage",
                path = Environment.getExternalStorageDirectory().absolutePath
            )
        )
    }
}