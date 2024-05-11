package com.simplespace.android.lib.standard.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.simplespace.android.lib.standard.type.Primitive

fun preferencesPairOf(
    name: String, type: Primitive, value: Any,
) : Preferences.Pair<*> {
    return when (type) {
        Primitive.BOOLEAN -> booleanPreferencesKey(name) to value as Boolean
        Primitive.INT -> intPreferencesKey(name) to value as Int
        Primitive.LONG -> longPreferencesKey(name) to value as Long
        Primitive.FLOAT -> floatPreferencesKey(name) to value as Float
        Primitive.DOUBLE -> doublePreferencesKey(name) to value as Double
        Primitive.STRING -> stringPreferencesKey(name) to value as String
        Primitive.STRING_SET -> stringSetPreferencesKey(name) to value as Set<String>
    }
}