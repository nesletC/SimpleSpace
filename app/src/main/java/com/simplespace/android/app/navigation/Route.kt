package com.simplespace.android.app.navigation

sealed class Route(val route: String) {

    data object Space: Route("Space")

    data object SpaceEdit: Route("SpaceEdit")
    data object SpaceFileEdit: Route("SpaceFileEdit")
    data object SpaceAuthentication: Route("SpaceAuthentication")
    data object SpaceFiles: Route("SpaceFiles")
    data object SpaceFileManagerExternal: Route( "SpaceFilesImport")

    data object App: Route("App")

    data object Welcome: Route("Welcome")
    data object Settings: Route("Settings")
    data object About: Route("About")
}