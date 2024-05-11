package com.simplespace.android.app.data.permissions

import android.Manifest
import android.os.Environment
import com.simplespace.android.app.App
import com.simplespace.android.lib.simple.permissions.SimplePermissions
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.permission.PermissionDataWithSettings
import com.simplespace.android.lib.standard.permission.PermissionDefinition

object AppPermissions : SimplePermissions<AppPermission>(

    AppPermission.MANAGE_EXTERNAL_STORAGE to PermissionDefinition.IndirectRequest(
        data = PermissionDataWithSettings(
            permissionLocationIntent = AndroidIntent.APP_PERMISSION_MANAGE_EXTERNAL_STORAGE,
            label = "Storage management",
            description = "save and load files in the public storage",
            state = Environment::isExternalStorageManager
        )
    ),

    AppPermission.WRITE_EXTERNAL_STORAGE to PermissionDefinition.IndirectRequest(
        data = PermissionDataWithSettings(
            permissionLocationIntent = AndroidIntent.APP_SETTINGS,
            label = "Write Files",
            description = "modify files in external storage",
            state = {
                App.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        ),
    ),

    AppPermission.MICROPHONE to PermissionDefinition.DirectRequest(
        data = PermissionDataWithSettings(
            permissionLocationIntent = AndroidIntent.LOCAL_APP_SETTINGS,
            label = "Microphone",
            description = "record audio"
        ),
        id = Manifest.permission.RECORD_AUDIO
    ),

    AppPermission.CAMERA to PermissionDefinition.DirectRequest(
        data = PermissionDataWithSettings(
            permissionLocationIntent = AndroidIntent.LOCAL_APP_SETTINGS,
            label = "Camera",
            description = "take pictures"
        ),
        id = Manifest.permission.CAMERA
    ),

    AppPermission.LOCATION to PermissionDefinition.DirectRequest(
        data = PermissionDataWithSettings(
            permissionLocationIntent = AndroidIntent.LOCAL_APP_SETTINGS,
            label = "Location",
            description = "locating the device",
        ),
        id = Manifest.permission.LOCATION_HARDWARE
    )
)