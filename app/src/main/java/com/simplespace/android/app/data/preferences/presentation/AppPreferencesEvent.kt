package com.simplespace.android.app.data.preferences.presentation

import com.simplespace.android.app.data.preferences.domain.model.AppPreference

sealed class AppPreferencesEvent {

    data object Clear: AppPreferencesEvent()
    data object Reset: AppPreferencesEvent()
    class Set (val data: Map<AppPreference, Any>): AppPreferencesEvent()
}