package com.simplespace.android.lib.standard.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


fun preferencesDataStoreOf(
    dataStore: DataStore<Preferences>
) : PreferencesDataStore = PreferencesDataStoreImplementation(dataStore)

private class PreferencesDataStoreImplementation(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataStore {

    override suspend fun <T> emit(key: Preferences.Key<T>): Flow<T>
    = dataStore.data.map {
        it[key]
            ?:throw NullPointerException()
    }

    override suspend fun emitAll(): Flow<Preferences> = dataStore.data

    override suspend fun <T> get(key: Preferences.Key<T>): T
    = dataStore.data.first()[key]!!

    override suspend fun <T> put(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it += key to value
        }
    }

    override suspend fun putAll(vararg pairs: Preferences.Pair<*>) {
        dataStore.edit {
            it.putAll(*pairs)
        }
    }

    override suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit {
            it.remove(key)
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}