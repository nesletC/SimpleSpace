package com.simplespace.android.app.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderStyle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simplespace.android.app.data.language.Language
import com.simplespace.android.app.data.language.Languages
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.domain.model.AppPreferencesData
import com.simplespace.android.app.data.preferences.presentation.AppPreferencesEvent
import com.simplespace.android.app.data.preferences.presentation.AppPreferencesSaverViewModel
import com.simplespace.android.lib.simple.color.SimpleColors
import com.simplespace.android.lib.simple.color.mode.SimpleColorModes
import com.simplespace.android.lib.simple.compose.button.SimpleButton
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlay
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlayConfirmation
import com.simplespace.android.lib.simple.compose.overlay.SimpleOverlayLoading
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.basic.storage.DeleteStorageViewModel
import com.simplespace.android.lib.simple.style.SimpleDesignStyle
import com.simplespace.android.lib.simple.style.SimpleDesignStyles
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.math.ui_size.ScreenUnitConversion
import kotlinx.coroutines.flow.collectLatest
import kotlin.system.exitProcess

@Composable
fun AppScreenScope.SettingsScreen(
    preferences: AppPreferencesData,
    preferencesViewModel: AppPreferencesSaverViewModel
) {

    fun changePreferences(map: Map<AppPreference, Any>) {
        preferencesViewModel.onEvent(AppPreferencesEvent.Set(map))
    }

    fun increaseTextSize() {

        if (preferences.textSize > 25) return
        changePreferences(mapOf(AppPreference.TEXT_SIZE to preferences.textSize + 1))
    }

    fun decreaseTextSize() {

        if (preferences.textSize < 10) return
        changePreferences(mapOf(AppPreference.TEXT_SIZE to preferences.textSize - 1))
    }


    @Composable
    fun SettingsButtonDivider() =
        Box(modifier = Modifier
            .width(
                screenUnitConverter
                    .convert(2, ScreenUnitConversion.PX_TO_DP).dp
            )
            .height(fontSize.h1.lineHeight)
        )

    @Composable
    fun SettingsTitle(
        name: String,
        image: ImageVector,
        contentDescription: String,
    ) {
        texts.h2( name )
        SpacerW(10.dp)
        SettingsButtonDivider()
        SpacerW(10.dp)
        Icon(imageVector = image, contentDescription = contentDescription)
    }

    @Composable
    fun SettingsButton(
        name: String,
        image: ImageVector,
        contentDescription: String,
        onClick: () -> Unit
    ) =
        mediumButtons.Button(onClick = onClick) {
            SettingsTitle(name, image, contentDescription)
        }

    val overlayLanguagesVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayTextSizeVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayColorModesVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayDesignVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayResetPreferencesConfirmationVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayDeleteAllConfirmationVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val overlayLoadingVisible = rememberSaveable {
        mutableStateOf(false)
    }

    Column (
        modifier = fillMaxWidth,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SpacerH(10.dp)

        SettingsButton(
            "Languages",
            Icons.Default.Language,
            "Set the language"
        ) {
            overlayLanguagesVisible.value = true
        }

        SpacerH(10.dp)

        SettingsButton(
            "Text Size",
            Icons.Default.ZoomIn,
            "Set the text size"
        ) {
            overlayTextSizeVisible.value = true
        }

        SpacerH(20.dp)

        Row {
            SettingsTitle(
                name = "Night mode",
                image = Icons.Default.ModeNight,
                contentDescription = "Set the night mode using multi toggle"
            )
            SpacerW(10.dp)

            mediumButtons.ToggleButtons(
                toggleStates = listOf(
                    "auto", "on", "off"
                ),
                currentSelection =
                if (preferences.nightModeAuto)
                    0
                else if (preferences.nightMode) 1
                else 2,
            ) {

                changePreferences(
                    mapOf(
                        AppPreference.NIGHT_MODE_AUTO to (it == 0),
                        AppPreference.NIGHT_MODE to (it == 1),
                    )
                )
            }
        }

        SpacerH(20.dp)

        Row {
            SettingsTitle(
                name = "Smooth mode",
                image = Icons.Default.FlashlightOff,
                contentDescription = "Set the smooth mode using multi toggle"
            )

            SpacerW(10.dp)

            mediumButtons.ToggleButtons(
                toggleStates = listOf(
                    "on", "off"
                ),
                currentSelection =
                if (preferences.smoothMode)
                    0
                else 1,

                ) {
                changePreferences(
                    mapOf(
                        AppPreference.SMOOTH_MODE to (it == 0)
                    )
                )
            }
        }

        SpacerH(20.dp)

        SettingsButton(
            "Color mode",
            Icons.Default.ColorLens,
            "Set the color mode"
        ) {
            overlayColorModesVisible.value = true
        }

        SpacerH(10.dp)

        SettingsButton(
            "Style",
            Icons.Default.BorderStyle,
            "Set the style"
        ) {
            overlayDesignVisible.value = true
        }

        SpacerH(10.dp)

        SettingsButton(
            "Reset all preferences",
            Icons.Default.Refresh,
            "Reset all preferences"
        ) {
            overlayResetPreferencesConfirmationVisible.value = true
        }

        SpacerH(10.dp)

        SettingsButton(
            "Delete all data",
            Icons.Default.Delete,
            "Delete all data"
        ) {
            overlayDeleteAllConfirmationVisible.value = true
        }

        SpacerH(10.dp)
    }



    // ---------------------- OVERLAYS -------------------------------------


    SimpleOverlay(
        visible = overlayLanguagesVisible.value,
        background = theme.surfaceBackground,
        escape = {
            overlayLanguagesVisible.value = false
        }
    ) {

        val languages = Languages.entries
        for (index in languages.indices) {

            val language = languages[index]

            mediumButtons.Button(onClick = {
                changePreferences(
                    mapOf(
                        AppPreference.LANGUAGE to index
                    )
                )
            }) {
                SpacerW(10.dp)
                Text(text = when (language) {
                    Language.ENGLISH -> "English"
                    Language.GERMAN -> "German (not yet supported)"
                })
                SpacerW(10.dp)
                Box(modifier = Modifier
                    .width(maxWidthCalculations.px(1).dp)
                    .background(color = Color.Gray)
                )
                SpacerW(10.dp)
                Text(text = Languages.code(language))
            }
        }
    }

    SimpleOverlay(
        visible = overlayTextSizeVisible.value,
        background = theme.surfaceBackground,
        escape = {
            overlayTextSizeVisible.value = false
        }
    ) {
        SpacerH(height = 20.dp)

        texts.font("Text Size")

        SpacerH(height = 20.dp)

        Row {

        }
        mediumButtons.IconButton(
            icon = Icons.Default.ZoomIn,
            contentDescription = "Increase Text Size",
        ) {
            increaseTextSize()
        }

        SpacerW(10.dp)

        mediumButtons.IconButton(
            icon = Icons.Default.ZoomOut,
            contentDescription = "Decrease Text Size",
        ) {
            decreaseTextSize()
        }

        SpacerH(10.dp)
    }

    SimpleOverlay(
        visible = overlayColorModesVisible.value,
        background = theme.surfaceBackground,
        escape = {
            overlayColorModesVisible.value = false
        }
    ) {
        @Composable
        fun colorModeButton(key: Int) {

            val color = SimpleColorModes.entries[key]

            Box(
                Modifier
                    .requiredSize(fontSize.title.lineHeight)
                    .background(
                        SimpleColors.simpleColor(
                            SimpleColorModes.simpleColor(color, nightMode)
                        )
                    )
                    .clickable {
                        changePreferences(
                            mapOf(
                                AppPreference.COLOR_MODE to key
                            )
                        )
                    }
            )
        }

        Row (
            Modifier.horizontalScroll(rememberScrollState())
        ){
            SpacerW(width = 20.dp)
            for (colorIndex in SimpleColorModes.entries.indices) {
                colorModeButton(key = colorIndex)
                SpacerW(width = 20.dp)
            }
        }
    }

    SimpleOverlay(
        visible = overlayDesignVisible.value,
        background = theme.surfaceBackground,
        escape = {
            overlayDesignVisible.value = false
        }
    ) {
        @Composable
        fun changeStyleButton(key: Int) {

            val style = SimpleDesignStyles.entries[key]

            SimpleButton(
                borderWidth = standardBorderWidth,
                paddingHorizontal = 10.dp,
                paddingVertical = 5.dp,
                theme = preferences.copy(
                    designStyle = key
                )
                    .theme(nightMode),
                onClick = {
                    changePreferences(
                        mapOf(
                            AppPreference.DESIGN_STYLE to key
                        )
                    )
                },
            ) {
                Text(when(style) {
                    SimpleDesignStyle.OUTLINE -> "Outline"
                    SimpleDesignStyle.FILLED -> "Filled"
                    SimpleDesignStyle.BARE -> "Bare"
                })
            }
        }
        SpacerH(10.dp)
        for (styleIndex in SimpleDesignStyle.entries.indices) {
            changeStyleButton(key = styleIndex)
            SpacerH(10.dp)
        }
    }

    SimpleOverlayLoading(
        visible = overlayLoadingVisible.value,
        borderWidth = standardBorderWidth
    )

    if (overlayDeleteAllConfirmationVisible.value) {

        overlayLoadingVisible.value = true

        val deleteStorage = viewModel<DeleteStorageViewModel>()

        val controlOverlay = remember { mutableStateOf(false) }

        SimpleOverlayConfirmation(
            visible = controlOverlay.value,
            message = "DELETE ALL DATA?",
            label = "Confirm",
            escape = { overlayDeleteAllConfirmationVisible.value = false }
        ) {
            preferencesViewModel.onEvent(AppPreferencesEvent.Clear)
            deleteStorage()
        }

        LaunchedEffect(key1 = Unit){

            var storageDeletionComplete = false
            var globalPreferencesDeletionComplete = false

            deleteStorage.deletionComplete.collectLatest{

                storageDeletionComplete = true

                if (globalPreferencesDeletionComplete)
                    exitProcess(0)
            }

            preferencesViewModel.onPreferencesUpdated.collectLatest{

                globalPreferencesDeletionComplete = true

                if (storageDeletionComplete)
                    exitProcess(0)
            }
        }

        overlayLoadingVisible.value = false

        controlOverlay.value = true
    }

    if (overlayResetPreferencesConfirmationVisible.value) {

        overlayLoadingVisible.value = true

        val controlOverlay = remember { mutableStateOf(false) }

        SimpleOverlayConfirmation(
            visible = controlOverlay.value,
            message = "Reset all preferences?",
            label = "Confirm",
            escape = { overlayResetPreferencesConfirmationVisible.value = false }
        ) {
            preferencesViewModel.onEvent(AppPreferencesEvent.Reset)
        }

        overlayLoadingVisible.value = false

        controlOverlay.value = true
    }
}