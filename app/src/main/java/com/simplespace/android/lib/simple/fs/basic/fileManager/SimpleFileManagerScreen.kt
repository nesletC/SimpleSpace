package com.simplespace.android.lib.simple.fs.basic.fileManager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplespace.android.app.lib.screen.AppScreenScope
import com.simplespace.android.lib.standard.composable.modifier.fillMaxHeight
import com.simplespace.android.lib.standard.composable.modifier.fillMaxSize
import com.simplespace.android.lib.standard.composable.modifier.fillMaxWidth
import com.simplespace.android.lib.standard.composable.remember.rememberSaveableList
import com.simplespace.android.lib.standard.composable.spacer.SpacerH
import com.simplespace.android.lib.standard.composable.spacer.SpacerW
import com.simplespace.android.lib.standard.string.nullIfBlank

@Composable
fun AppScreenScope.SimpleFileManagerScreen(
    viewModel: SimpleFileManagerViewModel
) {

    val purpose = viewModel.receiver!!

    val rootDirectory = purpose.fileManagerDirectory

    val isPurposeSelection =
        viewModel.receiver is SimpleFileManagerReceiver.FileSelection

    val contentState =
        viewModel.directoryContent.collectAsStateWithLifecycle()

    val content = contentState.value!!

    val directorySelection = rememberSaveableList<Int>()

    val fileSelection = rememberSaveableList<Int>()

    val isSelecting = rememberSaveable{
        mutableStateOf(false)
    }

    fun clearSelection() {
        fileSelection.clear()
        directorySelection.clear()
        isSelecting.value = false
    }

    fun parent() {

        if (rootDirectory.getLevel() > viewModel.fileManagerDirectoryMinLevel) {
            rootDirectory.parent()
            viewModel.onEvent(SimpleFileManagerEvent.DirectoryUpdated)
        }
    }

    fun child(directoryName: String) {

        rootDirectory.child(directoryName)
        viewModel.onEvent(SimpleFileManagerEvent.DirectoryUpdated)
    }
    Box(fillMaxSize){

        Column(
            modifier = fillMaxSize
        ) {
            Row(
                modifier = Modifier.height(fontSize.title.lineHeight * 2),
                verticalAlignment = Alignment.CenterVertically
            ){

                AnimatedVisibility(visible = !isSelecting.value){

                    Row {

                        SpacerW(width = 15.dp)

                        mediumButtons.IconButton(
                            icon = Icons.Default.ArrowUpward,
                            contentDescription = "Move up to parent directory"
                        ) {
                            parent()
                        }
                    }
                }

                AnimatedVisibility(visible = !isPurposeSelection) {

                    Row {

                        SpacerW(width = 15.dp)

                        mediumButtons.Button(onClick = {
                            viewModel.onEvent(
                                SimpleFileManagerEvent.OnResult(
                                    result = SimpleFileManagerResult.Directory
                                )
                            )
                        }) {

                            mediumIcons.Icon(icon = Icons.Default.Check)
                        }
                    }
                }

                SpacerW(15.dp)

                texts.title(rootDirectory.name.nullIfBlank()?:"Storage")
            }

            Column(
                modifier = fillMaxHeight
                    .verticalScroll(rememberScrollState())
            ) {
                content.directories.forEachIndexed { index, it ->

                    val selectionIndex = directorySelection.indexOf(index)

                    val isSelected = selectionIndex != -1

                    Row(
                        modifier = fillMaxWidth
                            .clickable {
                                if (isSelecting.value) {

                                    if (isSelected)
                                        directorySelection.removeAt(selectionIndex)
                                    else
                                        directorySelection.add(index)
                                } else
                                    child(it)
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AnimatedVisibility(visible = isSelecting.value) {

                            SpacerW(width = 10.dp)

                            mediumIcons.CheckBox(isChecked = isSelected)
                        }

                        SpacerW(10.dp)

                        smallIcons.Icon(icon = Icons.Default.Folder)

                        SpacerW(width = 10.dp)

                        texts.font(it)
                    }
                }

                content.files.forEachIndexed { index, fileName ->

                    SpacerH(height = 5.dp)

                    val selectionIndex = fileSelection.indexOf(index)

                    val isSelected = selectionIndex != -1

                    Row(
                        modifier = fillMaxWidth
                            .clickable(
                                onClick = {

                                    if (isSelecting.value) {

                                        if (isSelected)
                                            fileSelection.removeAt(selectionIndex)
                                        else
                                            fileSelection.add(index)
                                    }
                                }
                            )
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AnimatedVisibility(
                            visible = isSelecting.value
                        ) {

                            SpacerW(width = 10.dp)

                            mediumIcons.CheckBox(isChecked = isSelected)
                        }

                        SpacerW(width = 10.dp)

                        smallIcons.Icon(icon = Icons.AutoMirrored.Filled.InsertDriveFile)

                        SpacerW(width = 5.dp)

                        texts.font(fileName)
                    }
                }

                SpacerH(fontSize.title.lineHeight * 2)
            }
        }

        AnimatedVisibility(
            visible = isPurposeSelection,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ) {

            val anythingSelected = directorySelection.size > 0 || fileSelection.size > 0

            Box(
                Modifier
                    .clickable {

                        if (isSelecting.value) {
                            if (anythingSelected) {
                                directorySelection.clear()
                                fileSelection.clear()
                            } else {
                                directorySelection.addAll(content.directories.indices)
                                fileSelection.addAll(content.files.indices)
                            }

                        } else
                            isSelecting.value = true
                    }
                    .background(theme.surfaceBackground)
            ) {

                Box(Modifier.padding(10.dp)) {

                    mediumIcons.CheckBox(
                        isChecked = directorySelection.size > 0 || fileSelection.size > 0
                    )
                }
            }

            SpacerW(20.dp)
        }

        AnimatedVisibility(
            visible = isSelecting.value,
            modifier = fillMaxWidth
                .align(Alignment.BottomCenter)
                .height(
                    fontSize.title.lineHeight * 2
                )
        ) {

            Row(
                modifier = fillMaxSize
                    .background(theme.surfaceBackground)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                val abortLabel = "Abort"

                mediumButtons.IconTextButton(
                    icon = Icons.Default.Close,
                    contentDescription = abortLabel,
                    text = abortLabel,
                    onClick = ::clearSelection
                )

                SpacerW(10.dp)

                val confirmLabel = "Confirm"

                mediumButtons.IconTextButton(
                    icon = Icons.Default.Check,
                    contentDescription = confirmLabel,
                    text = confirmLabel,
                ) {

                    viewModel.onEvent(
                        SimpleFileManagerEvent.OnResult(
                            result = SimpleFileManagerResult.Files(
                                directorySelection = directorySelection,
                                fileSelection = fileSelection,
                            )
                        )
                    )
                }
            }
        }
    }
}