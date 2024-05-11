package com.simplespace.android.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.simplespace.android.app.App
import com.simplespace.android.app.data.preferences.domain.model.AppPreference
import com.simplespace.android.app.data.preferences.presentation.AppPreferencesEvent
import com.simplespace.android.app.data.preferences.presentation.AppPreferencesSaverViewModel
import com.simplespace.android.app.data.preferences.presentation.AppPreferencesViewModel
import com.simplespace.android.app.lib.screen.AppScreen
import com.simplespace.android.app.lib.util.AppScreen
import com.simplespace.android.app.presentation.AboutScreen
import com.simplespace.android.app.presentation.SettingsScreen
import com.simplespace.android.app.presentation.WelcomeScreen
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerScreen
import com.simplespace.android.lib.simple.fs.basic.fileManager.SimpleFileManagerViewModel
import com.simplespace.android.lib.standard.composable.navigation.sharedViewModel
import com.simplespace.android.lib.standard.composable.navigation.viewModelCreator
import com.simplespace.android.spaces.presentation.SpaceEvent
import com.simplespace.android.spaces.presentation.SpacesViewModel
import com.simplespace.android.spaces.presentation.authentication.SpaceAuthenticationScreen
import com.simplespace.android.spaces.presentation.authentication.SpaceAuthenticationViewModel
import com.simplespace.android.spaces.presentation.edit.SpaceEditScreen
import com.simplespace.android.spaces.presentation.edit.SpaceEditViewModel
import com.simplespace.android.spaces.presentation.fileEdit.SpaceFileEditScreen
import com.simplespace.android.spaces.presentation.files.SpaceFilesScreen
import com.simplespace.android.spaces.presentation.files.SpaceFilesViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavigationGraph(
    navController: NavHostController,
) {

    fun navigate(route: Route) = navController.navigate(route.route)

    val globalPreferencesViewModel =
        hiltViewModel<AppPreferencesViewModel>()

    globalPreferencesViewModel.update()

    val globalPreferencesState =
        globalPreferencesViewModel.current.collectAsStateWithLifecycle()

    val globalPreferences = globalPreferencesState.value

    if (globalPreferences != null) {

        NavHost(navController = navController, startDestination = Route.Space.route) {

            navigation(
                startDestination = Route.SpaceFiles.route,
                route = Route.Space.route,
            ) {

                composable(route = Route.SpaceFiles.route) {

                    if (App.launched || !globalPreferences.showTutorial) {

                        val spacesViewModel =
                            it.sharedHiltViewModel<SpacesViewModel>(
                                navController = navController
                            )

                        val viewModel = viewModel<SpaceFilesViewModel>()

                        val externalFileManagerViewModel = it.sharedViewModel(
                            navController = navController
                        ) {
                            SimpleFileManagerViewModel()
                        }

                        SpaceFilesScreen(
                            preferences = globalPreferences,
                            spacesViewModel = spacesViewModel,
                            viewModel = viewModel,
                            externalFileManagerViewModel = externalFileManagerViewModel,
                            navigate = ::navigate
                        )

                    }
                    else {

                        navigate(Route.Welcome)

                        App.launched = true
                    }
                }

                composable(route = Route.SpaceAuthentication.route) {

                    val spaceViewModel =
                        it.sharedHiltViewModel<SpacesViewModel>(
                            navController = navController
                        )

                    val action = spaceViewModel.spaceAuthenticationAction

                    if (action != null) {


                        val viewModel = viewModelCreator {
                            SpaceAuthenticationViewModel(
                                request = action,
                            )
                        }

                        LaunchedEffect(Unit) {

                            viewModel.success.collect {

                                spaceViewModel.onEvent(SpaceEvent.OnAuthenticatedActionSuccessful)
                            }

                        }

                        LaunchedEffect(Unit) {

                            spaceViewModel.openBase.collect {

                                navController.popBackStack(Route.SpaceFiles.route, false)
                            }
                        }

                        SpaceAuthenticationScreen(
                            preferences = globalPreferences,
                            authenticator = viewModel,
                        )
                    }
                }

                composable(route = Route.SpaceEdit.route) { entry ->

                    val spacesViewModel =
                        entry.sharedHiltViewModel<SpacesViewModel>(
                            navController = navController
                        )

                    val viewModel = viewModelCreator{
                        SpaceEditViewModel(spacesViewModel.spaceSavingEvent!!)
                    }

                    val externalFileManagerViewModel =
                        entry.sharedViewModel(
                            navController = navController
                        ) {
                            SimpleFileManagerViewModel()
                        }

                    LaunchedEffect(Unit) {

                        spacesViewModel.openBase.collect{
                            navController.popBackStack(
                                Route.SpaceFiles.route, false
                            )
                        }
                    }

                    LaunchedEffect(Unit) {

                        viewModel.authenticationAction.collect{
                            spacesViewModel.onEvent(SpaceEvent.LaunchingAuthentication(
                                it
                            ))
                        }
                    }

                    LaunchedEffect(Unit) {

                        spacesViewModel.openAuthentication.collect {
                            navigate(Route.SpaceAuthentication)
                        }
                    }

                    LaunchedEffect(Unit) {

                        externalFileManagerViewModel.openFileManager.collect{
                            navigate(Route.SpaceFileManagerExternal)
                        }
                    }

                    AppScreen(globalPreferences) {

                        AppScreen(title = "Save", navigate = ::navigate) {

                            SpaceEditScreen(
                                viewModel = viewModel,
                                externalFileManagerViewModel = externalFileManagerViewModel
                            )
                        }
                    }
                }

                composable(route = Route.SpaceFileEdit.route) {


                    val viewModel =
                        it.sharedHiltViewModel<SpacesViewModel>(
                            navController = navController
                        )

                    AppScreen(preferences = globalPreferences) {

                        SpaceFileEditScreen()
                    }
                }

                composable(route = Route.SpaceFileManagerExternal.route) {

                    val viewModel =
                        it.sharedViewModel(navController = navController) {
                            SimpleFileManagerViewModel()
                        }

                    LaunchedEffect(Unit){
                        viewModel.closeFileManager.collect {
                            navController.popBackStack()
                        }
                    }

                    AppScreen(preferences = globalPreferences) {

                        SimpleFileManagerScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }

            navigation(
                startDestination = Route.Welcome.route,
                route = Route.App.route,
            ) {

                composable(route = Route.Welcome.route) {

                    val viewModel =
                        hiltViewModel<AppPreferencesSaverViewModel>()

                    AppScreen(globalPreferences) {

                        AppScreen(title = "Tutorial", navigate = ::navigate) {

                            WelcomeScreen(
                                navigate = ::navigate,
                                showTutorial = globalPreferences.showTutorial,
                                changeShowingTutorialPreference = {
                                    viewModel.onEvent(
                                        AppPreferencesEvent.Set(
                                        mapOf(
                                            AppPreference.SHOW_TUTORIAL to it,
                                        )
                                    ))
                                }
                            )
                        }
                    }
                }

                composable(route = Route.Settings.route) {


                    val viewModel =
                        hiltViewModel<AppPreferencesSaverViewModel>()


                    LaunchedEffect(key1 = Unit) {

                        viewModel.onPreferencesUpdated.collectLatest{
                            globalPreferencesViewModel.update()
                        }
                    }

                    AppScreen(globalPreferences) {

                        AppScreen(title = "Settings", navigate = ::navigate) {

                            SettingsScreen(
                                preferences = globalPreferences,
                                preferencesViewModel = viewModel
                            )
                        }
                    }
                }

                composable(route = Route.About.route) {


                    AppScreen(globalPreferences) {

                        AppScreen(title = "About", navigate = ::navigate) {

                            AboutScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedHiltViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel(this)

    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}