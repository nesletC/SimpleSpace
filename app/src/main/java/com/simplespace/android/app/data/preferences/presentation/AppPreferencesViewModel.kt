package com.simplespace.android.app.data.preferences.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.standard.preferences.BasicPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppPreferencesViewModel @Inject constructor(
    private val repository: BasicPreferences<AppPreference, AppPreferencesData>
) : ViewModel() {

    private val currentState =
        MutableStateFlow<AppPreferencesData?>(null)

    val current = currentState.asStateFlow()

    fun update() {
        viewModelScope.launch {
            currentState.update {
                repository.get()
            }
        }
    }
}