package com.simplespace.android.app.data.preferences.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.COLOR_MODE
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.DESIGN_STYLE
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.LANGUAGE
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.NIGHT_MODE
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.NIGHT_MODE_AUTO
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.SHOW_TUTORIAL
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.SMOOTH_MODE
import com.simplespace.android.app.data.preferences.domain.model.AppPreference.TEXT_SIZE
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.lib.standard.comparison.equalsAny
import com.simplespace.android.lib.standard.preferences.BasicPreferencesImplementation
import com.simplespace.android.lib.standard.type.Primitive

class AppPreferencesRepository(
    context: Context
) : BasicPreferencesImplementation<AppPreference, AppPreferencesData>(
    context.dataStore, AppPreferencesData()
) {
    override fun type(key: AppPreference): Primitive =
        if (key.equalsAny(NIGHT_MODE, NIGHT_MODE_AUTO, SMOOTH_MODE, SHOW_TUTORIAL))
            Primitive.BOOLEAN
        else
            Primitive.INT

    override fun map(data: AppPreferencesData): Map<AppPreference, Any> = mapOf(
        NIGHT_MODE to data.nightMode,
        NIGHT_MODE_AUTO to data.nightModeAuto,
        TEXT_SIZE to data.textSize,
        COLOR_MODE to data.colorMode,
        SMOOTH_MODE to data.smoothMode,
        DESIGN_STYLE to data.designStyle,
        LANGUAGE to data.language,
        SHOW_TUTORIAL to data.showTutorial,
    )

    override fun data(map: Map<AppPreference, Any>): AppPreferencesData =
        AppPreferencesData(
            nightMode = map[NIGHT_MODE] as Boolean,
            nightModeAuto = map[NIGHT_MODE_AUTO] as Boolean,
            textSize = map[TEXT_SIZE] as Int,
            colorMode = map[COLOR_MODE] as Int,
            smoothMode = map[SMOOTH_MODE] as Boolean,
            designStyle = map[DESIGN_STYLE] as Int,
            language = map[LANGUAGE] as Int,
            showTutorial = map[SHOW_TUTORIAL] as Boolean,
        )

    override val keyString: Map<AppPreference, String> = mapOf(
        NIGHT_MODE to "nightMode",
        NIGHT_MODE_AUTO to "nightModeAuto",
        TEXT_SIZE to "textSize",
        COLOR_MODE to "colorMode",
        SMOOTH_MODE to "smoothMode",
        DESIGN_STYLE to "designStyle",
        LANGUAGE to "language",
        SHOW_TUTORIAL to "showTutorial",
    )
}


private const val storeName = "GlobalPreferences"

private val Context.dataStore by preferencesDataStore(storeName)