package com.simplespace.android.lib.standard.preferences

import androidx.datastore.preferences.core.Preferences
import com.simplespace.android.lib.standard.iterable.map.mapKeysOnly
import com.simplespace.android.lib.standard.iterable.map.mapOf
import com.simplespace.android.lib.standard.type.Primitive

interface BasicPreferences <Key: Enum<Key>, Data> {

    val default: Data

    fun type(key: Key) : Primitive

    val key: Map<String, Key>

    val keyString : Map<Key, String>

    fun map(data: Data) : Map<Key, Any>

    fun data(map: Map<Key, Any>) : Data

    suspend fun clear()

    suspend fun set(preferences: Map<Key, Any>)

    fun map(data: Data, map: Map<Key, Any>) : Map<Key, Any> = mapOf{

        putAll(map(data))
        putAll(map)
    }

    fun data(data: Data, map: Map<Key, Any>) : Data = data(map(data, map))

    fun data(preferences: Preferences) : Data =
        data(
            default,
            preferences.asMap().mapKeysOnly {
                key[name]!!
            }
        )

    suspend fun reset() = set(default)

    suspend fun set(data: Data) = set(map(data))

    suspend fun get() : Data
}