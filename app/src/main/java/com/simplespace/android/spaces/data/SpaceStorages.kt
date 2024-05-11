package com.simplespace.android.spaces.data

import com.simplespace.android.app.App
import com.simplespace.android.lib.simple.fs.basic.directory.SimpleDirectory.onChild

// this object needs to be initialized by calling the initialize(SimpleStorages) function

object SpaceStorages {

    private const val spacesRootFolder = "simple_aes"

    fun getProtectedDirectory(fileName: String,) =
        internalStorage.run {

            onChild(fileName){
                mutableCopy()
            }
        }

    fun getAppDirectory(fileName: String) =
        externalAppStorage?.run {

            onChild(fileName){
                mutableCopy()
            }
        }

    private var externalAppStorage = App.mainRootDirectories.app()

    private var internalStorage = App.mainRootDirectories.protected()

    init {

        internalStorage.run {

            child(spacesRootFolder)

            if (!file.exists())
                file.mkdir()
        }

        externalAppStorage?.run {

            child(spacesRootFolder)

            if (!file.exists())
                file.mkdir()
        }
    }
}
