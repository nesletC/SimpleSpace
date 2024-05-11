package com.simplespace.android.app.data.preferences.dependency_injection

import android.app.Application
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.app.data.preferences.repository.AppPreferencesRepository
import com.simplespace.android.lib.standard.preferences.BasicPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppPreferencesModule {

    @Provides
    @ViewModelScoped
    fun provideGlobalPreferencesRepository(
        context: Application,
    ) : BasicPreferences<AppPreference, AppPreferencesData> =
        AppPreferencesRepository(context)
}