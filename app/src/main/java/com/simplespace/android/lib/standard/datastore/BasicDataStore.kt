package com.simplespace.android.lib.standard.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first


abstract class BasicDataStore(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun clear() { dataStore.edit { it.clear() } }

    suspend fun set(preferences: List<Preferences.Pair<*>>) {

        dataStore.edit {
            preferences.forEach{ pair ->
                it += pair
            }
        }
    }

    suspend fun collection(): Preferences = dataStore.data.first()
}