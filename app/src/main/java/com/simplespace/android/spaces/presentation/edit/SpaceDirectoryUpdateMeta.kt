package com.simplespace.android.spaces.presentation.edit

import androidx.compose.runtime.MutableState

class SpaceDirectoryUpdateMeta (

    val cancel: MutableState<Boolean>,

    val defaultFileName : String,

    val fileName : MutableState<String>,

    val defaultKey: ByteArray,

    val key : MutableState<ByteArray>,

    val defaultLabel : String,

    val label : MutableState<String>
)