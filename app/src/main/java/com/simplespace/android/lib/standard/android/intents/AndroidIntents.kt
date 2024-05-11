package com.simplespace.android.lib.standard.android.intents

import android.content.Intent
import android.provider.Settings
import com.simplespace.android.app.App

object AndroidIntents {

    private val map = mapOf(

        AndroidIntent.APP_SETTINGS to Intent(
            Settings.ACTION_APPLICATION_SETTINGS
        ),

        AndroidIntent.APP_PERMISSION_MANAGE_EXTERNAL_STORAGE to Intent(
            Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
        ),

        AndroidIntent.LOCAL_APP_SETTINGS to Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, App.AppUri
        )
    )

    val AndroidIntent.intent get() = map[this]!!
}