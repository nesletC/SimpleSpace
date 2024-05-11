package com.simplespace.android.app

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import androidx.activity.ComponentActivity
import com.simplespace.android.lib.simple.storage.SimpleMainRootDirectories
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.android.intents.AndroidIntents.intent
import com.simplespace.android.lib.standard.storage.MainRootDirectoryPaths

object App {

    var launched = false

    private lateinit var _launch: (Intent) -> Unit

    fun launch(intentAction: String, useAppUri: Boolean = false) {
        launch(
            when (useAppUri) {
                true -> Intent(intentAction, AppUri)
                false -> Intent(intentAction)
            }
        )
    }

    fun launch(intent: Intent) = _launch(intent)

    fun launch(intent: AndroidIntent) = launch(intent.intent)

    lateinit var checkPermission: (String) -> Boolean

    lateinit var AppUri: Uri
        private set

    lateinit var mainRootDirectories: SimpleMainRootDirectories
    private set

    fun ComponentActivity.initializeApp() {

        if (!launched) {

            val rootDirectoryPaths = MainRootDirectoryPaths(applicationContext)

            mainRootDirectories = SimpleMainRootDirectories(rootDirectoryPaths)

            AppUri = Uri.fromParts("package", packageName, null)

            _launch = ::startActivity

            checkPermission = {
                checkSelfPermission(it) == PERMISSION_GRANTED
            }
        }
    }
}