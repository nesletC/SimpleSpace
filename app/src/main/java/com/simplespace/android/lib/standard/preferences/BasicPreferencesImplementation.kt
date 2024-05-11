package com.simplespace.android.lib.standard.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.simplespace.android.lib.standard.datastore.BasicDataStore
import com.simplespace.android.lib.standard.datastore.preferencesPairOf
import com.simplespace.android.lib.standard.iterable.list.list
import com.simplespace.android.lib.standard.iterable.map.inverted

abstract class BasicPreferencesImplementation<Key: Enum<Key>, Data, >(
    dataStore: DataStore<Preferences>,
    override val default: Data
) : BasicPreferences<Key, Data>, BasicDataStore(dataStore) {

    abstract override val keyString: Map<Key, String>

    override val key: Map<String, Key>
        get() = _keyString?:keyString.inverted().also {
            _keyString = it
        }

    override suspend fun get(): Data = data(collection())

    override suspend fun set(preferences: Map<Key, Any>) {
        set(
            list {
                preferences.forEach {
                    add(
                        preferencesPairOf(
                            keyString[it.key]!!,
                            type(it.key),
                            it.value
                        )
                    )
                }
            }
        )
    }

    private var _keyString: Map<String, Key>? = null
}