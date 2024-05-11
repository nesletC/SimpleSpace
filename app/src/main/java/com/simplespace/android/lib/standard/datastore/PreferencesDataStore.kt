package com.simplespace.android.lib.standard.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {
    suspend fun <T> emit(key: Preferences.Key<T>): Flow<T>
    suspend fun emitAll(): Flow<Preferences>

    suspend fun <T> get(key: Preferences.Key<T>):T?

    suspend fun <T> put(key: Preferences.Key<T>, value:T)
    suspend fun putAll(vararg pairs: Preferences.Pair<*>)

    suspend fun <T> remove(key: Preferences.Key<T>)
    suspend fun clear()
}