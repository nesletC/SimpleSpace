package com.simplespace.android.lib.standard.storage

class StorageState (
    val isIO: Boolean,
    val isReadOnly: Boolean,
) {
    val isMounted = isIO || isReadOnly
}