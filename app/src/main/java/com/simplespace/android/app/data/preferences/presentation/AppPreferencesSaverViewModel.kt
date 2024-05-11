package com.simplespace.android.app.data.preferences.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.standard.preferences.BasicPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppPreferencesSaverViewModel @Inject constructor(
    private val repository: BasicPreferences<AppPreference, AppPreferencesData>
) : ViewModel() {

    private val _onPreferencesUpdated = MutableSharedFlow<Unit>()
    val onPreferencesUpdated = _onPreferencesUpdated.asSharedFlow()

    fun onEvent(event: AppPreferencesEvent) {

        viewModelScope.launch {

            when (event) {
                AppPreferencesEvent.Clear ->
                    repository.clear()
                AppPreferencesEvent.Reset ->
                    repository.reset()
                is AppPreferencesEvent.Set ->
                    repository.set(event.data)
            }

            _onPreferencesUpdated.emit(Unit)
        }
    }
}
