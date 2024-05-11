package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simplespace.android.app.data.permissions.AppPermissions
import com.simplespace.android.app.data.permissions.AppPermission
import com.simplespace.android.app.navigation.Route
import com.simplespace.android.app.lib.util.AppNavigationButtons
import com.simplespace.android.lib.simple.color.theme.SimpleColorTheme
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.spaces.data.SpaceAuthentication
import com.simplespace.android.spaces.model.SpaceDirectoryReference
import com.simplespace.android.spaces.model.SpaceOverview
import com.simplespace.android.spaces.presentation.SpaceEvent

@Composable
fun AppScreenScope.SpaceFilesSideBar(
    selectedSpace: Int?,
    spaces: Map<Int, SpaceOverview>,
    dropDownVisibilityStates: SnapshotStateMap<Int, Boolean>,
    hide: () -> Unit,
    navigate: (Route) -> Unit,
    onEvent: (SpaceEvent) -> Unit,
    requestStoragePermission: () -> Unit,
) {

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(5.dp)
    ) {

        SpacerH(height = 15.dp)

        Row {
            SpacerW(width = 10.dp)

            texts.h1("Spaces")
        }

        SpacerH(height = 10.dp)

        Row(
            Modifier
                .clickable {
                    hide()
                    onEvent(SpaceEvent.LaunchingSpaceEdit.New)
                }
                .padding(5.dp)
        ) {

            smallIcons.Icon(icon = Icons.Default.Add, contentDescription = "Create")

            SpacerW(10.dp)

            texts.h3("Create")
        }

        Row(
            Modifier
                .clickable {
                    hide()
                    onEvent(
                        SpaceEvent.LaunchingAuthentication(
                            SpaceAuthentication.Action.Load
                        )
                    )
                }
                .padding(5.dp)
        ) {

            smallIcons.Icon(
                icon = Icons.AutoMirrored.Filled.Login,
                contentDescription = "Login"
            )

            SpacerW(10.dp)

            texts.h3("Log into a space")
        }

        spaces.forEach {

            SideBarSpace(
                index = it.key,
                overview = it.value,
                selectedSpace = selectedSpace,
                dropDownVisible = dropDownVisibilityStates[it.key],
                toggleDropDown = {
                    dropDownVisibilityStates[it.key] = !dropDownVisibilityStates[it.key]!!
                },
                onEvent = onEvent,
                hideSideBar = hide,
                requestStoragePermission = requestStoragePermission
            )

        }

        SpacerH(height = 10.dp)

        Divider(
            modifier = fillMaxWidth
                .background(Color.Gray)
                .height(0.5.dp)
        )

        SpacerH(height = 10.dp)

        AppNavigationButtons(navigate = navigate)
    }
}



@Composable
private fun AppScreenScope.SideBarSpace(
    index: Int,
    overview: SpaceOverview,
    selectedSpace: Int?,
    dropDownVisible: Boolean?,
    toggleDropDown: () -> Unit,
    onEvent: (SpaceEvent) -> Unit,
    hideSideBar: () -> Unit,
    requestStoragePermission: () -> Unit,
) {

    val selected = index == selectedSpace

    fun select(spaceDirectory: SpaceDirectoryReference) {
        hideSideBar()
        onEvent(SpaceEvent.OnDirectorySelected(index, spaceDirectory))
    }

    Row (
        modifier =(
                if (selected)
                    selectedSpaceNameRowModifier(theme)
                else
                    spaceNameRowModifier
                )
            .clickable {
                if (dropDownVisible != null)
                    toggleDropDown()
                else {
                    select(SpaceDirectoryReference.Protected)
                }
            },
    ) {

        Text(
            text = overview.name,
            fontSize = fontSize.h2.sp,
            color =
            if (selected)
                theme.content
            else
                theme.surfaceText
        )

        SpacerW(width = 5.dp)

        if (dropDownVisible != null)

            mediumIcons.DropDownIcon(isDropped = dropDownVisible)
    }

    if (dropDownVisible != null) {
        AnimatedVisibility(visible = dropDownVisible) {

            Column{

                Row(
                    modifier = fillMaxWidth
                        .clickable {
                            select(SpaceDirectoryReference.Protected)
                        }
                        .padding(5.dp)
                ) {
                    texts.font("Protected Directory")
                }
                if (overview.appDirectoryExists) {

                    Row(
                        modifier = fillMaxWidth
                            .clickable {
                                select(SpaceDirectoryReference.App)
                            }
                            .padding(5.dp)
                    ) {
                        texts.font("App directory")
                    }
                }
                overview.customDirectories.forEachIndexed { index, label ->

                    Row(
                        modifier = fillMaxWidth
                            .clickable {

                                val requiredPermission =
                                    AppPermission.MANAGE_EXTERNAL_STORAGE

                                if (AppPermissions.isGrantedCurrently(
                                        requiredPermission
                                    )
                                )
                                    select(SpaceDirectoryReference.Custom(index))
                                else
                                    requestStoragePermission()
                            }
                            .padding(5.dp)
                    ) {
                        texts.font(label)
                    }
                }
            }
        }
    }
}

private val spaceNameRowModifier = fillMaxWidth
    .padding(8.dp)

private fun selectedSpaceNameRowModifier(theme: SimpleColorTheme) = spaceNameRowModifier
    .background(color = theme.background)