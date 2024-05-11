package com.simplespace.android.spaces.presentation.files.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TextSnippet
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplespace.android.lib.simple.compose.dropDown.SimpleDropdown
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.simple.fs.aes.directory.AESContent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerEvent
import com.simplespace.android.lib.simple.fs.aes.manager.AESFileManagerSelectionIndices
import com.simplespace.android.lib.standard.composable.modifier.fillMaxHeight
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW

@Composable
fun AppScreenScope.SpaceFilesContent(
    isNothingOpened: Boolean,
    content: AESContent,
    temporarySelection: AESFileManagerSelectionIndices?,
    onFileManagerEvent: (AESFileManagerEvent) -> Unit,
) {

    Column(
        fillMaxHeight.verticalScroll(rememberScrollState())
    ) {

        SpacerH(height = 10.dp)

        if (isNothingOpened) {

            Box(fillMaxSize) {

                Box(Modifier.align(Alignment.Center)) {

                    texts.title(text = "SimpleSpaces")
                }
            }
        }
        else {

            FileList(
                list = content.aes.directories,
                isDirectories = true,
                label = "Encrypted directories",
                name = { meta.realName },
                selected = temporarySelection?.aesDirectories,
            ) {

                if (temporarySelection == null)
                    onFileManagerEvent(AESFileManagerEvent.OpenChild(it))
                else
                    onFileManagerEvent(
                        AESFileManagerEvent.TemporarySelection.Switch(
                            isEncrypted = true,
                            isDirectory = true,
                            index = it,
                        ))
            }

            FileList(
                list = content.aes.files,
                isDirectories = false,
                label = "Encrypted files",
                name = { meta.realName  },
                selected = temporarySelection?.aesFiles,
            ) {

                if (temporarySelection == null)
                    onFileManagerEvent(
                        AESFileManagerEvent.EncryptedFile.ShowDetails(
                            isDirectory = false,
                            index = it
                        )
                    )
                else
                    onFileManagerEvent(
                        AESFileManagerEvent.TemporarySelection.Switch(
                            isEncrypted = true,
                            isDirectory = false,
                            index = it,
                        ))
            }

            FileList(
                list = content.raw.directories,
                isDirectories = true,
                label = "Raw directories",
                name = { this },
                selected = temporarySelection?.rawDirectories,
            ) {

                if (temporarySelection == null)
                    onFileManagerEvent(
                        AESFileManagerEvent.File.ShowDetails(
                            isDirectory = true,
                            index = it
                        )
                    )
                else
                    onFileManagerEvent(
                        AESFileManagerEvent.TemporarySelection.Switch(
                            isEncrypted = false,
                            isDirectory = true,
                            index = it,
                        ))
            }

            FileList(
                list = content.raw.files,
                isDirectories = false,
                label = "Raw files",
                name = { this },
                selected = temporarySelection?.rawFiles,
                onClick = {

                    if (temporarySelection == null)
                        onFileManagerEvent(
                            AESFileManagerEvent.File.ShowDetails(
                                isDirectory = false,
                                index = it,
                            )
                        )
                    else
                        onFileManagerEvent(
                            AESFileManagerEvent.TemporarySelection.Switch(
                                isEncrypted = false,
                                isDirectory = false,
                                index = it,
                            ))
                }
            )
        }
    }
}


@Composable
fun <T> AppScreenScope.FileList(
    list: List<T>,
    isDirectories: Boolean,
    label: String,
    name: T.() -> String,
    selected: List<Boolean>?,
    onClick: (Int) -> Unit,
) {

    if (list.isNotEmpty()) {

        val showFiles = rememberSaveable{
            mutableStateOf(true)
        }

        SimpleDropdown(
            dropped = showFiles,
            title = label,
            titleFontSize = fontSize.h2.sp,
            titlePaddingHorizontal = 10.dp,
            titlePaddingVertical = 5.dp,
            dropdownIconSize = fontSize.h2.lineHeight,
        ) {

            Column{

                list.forEachIndexed { index, file ->

                    val isSelected: Boolean? =
                        if (
                            selected != null
                        )
                            selected[index]
                        else
                            null

                    FileDisplay(
                        isDirectory = isDirectories,
                        name = file.name(),
                        isSelected = isSelected,
                    ) {
                        onClick(index)
                    }
                }
            }
        }
    }
}

@Composable
private fun AppScreenScope.FileDisplay(
    isDirectory: Boolean,
    name: String,
    isSelected: Boolean?,
    onClick: () -> Unit,
) {
    Row (
        fillMaxWidth
            .padding(5.dp)
            .clickable(onClick = onClick)
    ){

        AnimatedVisibility(visible = isSelected != null) {

            mediumIcons.CheckBox(isChecked = isSelected?:false)
        }

        SpacerW(8.dp)

        mediumIcons.Icon(
            icon =
            if (isDirectory)
                Icons.Default.Folder
            else
                Icons.AutoMirrored.Filled.TextSnippet,
            contentDescription =
            if (isDirectory) FILE_DESCRIPTION
            else DIRECTORY_DESCRIPTION
        )

        SpacerW(8.dp)

        texts.font(name)
    }
}

private const val FILE_DESCRIPTION = "file"

private const val DIRECTORY_DESCRIPTION = "directory"