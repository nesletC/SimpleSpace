package com.simplespace.android.spaces.util

import com.simplespace.android.spaces.model.SpaceMeta

fun Map<Int, SpaceMeta>.names() = mapValues {
    it.value.realName
}