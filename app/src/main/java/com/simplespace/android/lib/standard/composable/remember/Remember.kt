package com.simplespace.android.lib.standard.composable.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import com.simplespace.android.lib.standard.iterable.map.mapKeysOnly

@Composable
fun <T: Any> rememberSaveableList(list: SnapshotStateList<T>) = rememberSaveable(
    saver = listSaver(
        save = { stateList ->
            if (stateList.isNotEmpty()) {
                val first = stateList.first()
                if (!canBeSaved(first)) {
                    throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                }
            }
            stateList.toList()
        },
        restore = { it.toMutableStateList() }
    )
) {
    list
}

@Composable
fun <T: Any> rememberSaveableList(
    init: SnapshotStateList<T>.() -> Unit = { }
) : SnapshotStateList<T> {

    val list = mutableStateListOf<T>()

    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        ),
        init = { list.apply(init) }
    )
}

@Composable
fun <T: Any> rememberSaveableIndexMap(
    init: SnapshotStateMap<Int, T>.() -> Unit = { }
) : SnapshotStateMap<Int, T> {

    val map = mutableStateMapOf<Int, T>()

    return rememberSaveable(
        saver = mapSaver(
            save = { stateMap ->

                if (stateMap.isNotEmpty()) {
                    val first = stateMap[stateMap.keys.first()]!!
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateMap.mapKeysOnly {
                    toString()
                }
            },
            restore = {
                mutableStateMapOf<Int, T>().apply {
                    it.forEach{
                        put(it.key.toInt(), it.value as T)
                    }
                }
            }
        ),
        init = { map.apply(init) }
    )
}