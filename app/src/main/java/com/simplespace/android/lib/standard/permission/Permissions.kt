package com.simplespace.android.lib.standard.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.simplespace.android.lib.standard.android.intents.AndroidIntent
import com.simplespace.android.lib.standard.inline.letIf
import com.simplespace.android.lib.standard.iterable.list.addIfNew
import kotlin.properties.Delegates

abstract class Permissions<T: Enum<T>> (
    vararg definitions: Pair<T, PermissionDefinition>
){

    private val mutableData =
        mutableMapOf<T, PermissionDataFinal>()

    private val mutablePermissionLocationIntents = mutableListOf<AndroidIntent>()

    private val directRequestPermissionsIDs = mutableMapOf<T, String>()
    private val directRequestPermissionsByID = mutableMapOf<String, T>()

    private val mutableState = mutableMapOf<T, PermissionState>()

    init {
        definitions.forEach { (permission, definition) ->

            val intent = definition.data.permissionLocationIntent

            mutablePermissionLocationIntents.addIfNew(intent)

            val stateGetter = definition.data.state

            mutableData[permission] = PermissionDataFinal(
                permissionLocationIntent = intent,
                label = definition.data.label,
                description = definition.data.description,
                state = stateGetter,
                isDirectRequest = when (definition) {
                    is PermissionDefinition.DirectRequest -> {

                        directRequestPermissionsIDs[permission] = definition.id
                        directRequestPermissionsByID[definition.id] = permission

                        true
                    }
                    is PermissionDefinition.IndirectRequest -> false
                }
            )

            mutableState[permission] = stateGetter?.invoke()?.letIf { PermissionState.GRANTED }
                ?:PermissionState.NOT_REQUESTED
        }
    }

    val state: Map<T, PermissionState> get() = mutableState

    val data: Map<T, PermissionDataFinal> get() = mutableData

    val settings: List<AndroidIntent> get() = mutablePermissionLocationIntents

    @Composable
    fun resultLauncher() : (T) -> Unit {

        var permission by Delegates.notNull<T>()

        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) {
                mutableState[permission] =
                    if (it) PermissionState.GRANTED else PermissionState.DENIED
            }

        return {

            permission = it

            launcher.launch(directRequestPermissionsIDs[it]!!)
        }
    }

    @Composable
    fun multipleResultsLauncher() : (List<T>) -> Unit {

        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { entries ->
                entries.forEach {

                    mutableState[directRequestPermissionsByID[it.key]!!] =
                        if (it.value) PermissionState.GRANTED
                        else PermissionState.DENIED
                }
            }

        return { requestedPermissions ->

            launcher.launch(
                Array(requestedPermissions.size) {
                    directRequestPermissionsIDs[requestedPermissions[it]]!!
                }
            )
        }
    }

    fun isGranted(permission: T) = mutableState[permission] == PermissionState.GRANTED

    fun isGrantedCurrently(permission: T) =
        mutableData[permission]!!.state?.invoke()?:(state[permission] == PermissionState.GRANTED)

    fun updateState(permission: T) = mutableData[permission]!!.state?.invoke()?.also{

        if (state[permission] == PermissionState.GRANTED)
            mutableState[permission] = PermissionState.NOT_REQUESTED
    }?:(state[permission] == PermissionState.GRANTED)
}