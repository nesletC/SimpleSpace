package com.simplespace.android.lib.simple.fs.aes.manager

import com.simplespace.android.lib.simple.fs.aes.directory.AESContent
import com.simplespace.android.lib.simple.fs.aes.directory.AESContentDataIndices

class AESFileManagerSelectionIndicesManager {

    private var _aesDirectoriesSelected = mutableListOf<Boolean>()
    private var _aesFilesSelected = mutableListOf<Boolean>()
    private var _rawDirectoriesSelected = mutableListOf<Boolean>()
    private var _rawFilesSelected = mutableListOf<Boolean>()
    private var _selectionCount = 0

    val aesDirectoriesSelected : List<Boolean> get() = _aesDirectoriesSelected
    val aesFilesSelected : List<Boolean> get() = _aesFilesSelected
    val rawDirectoriesSelected : List<Boolean> get() = _rawDirectoriesSelected
    val rawFilesSelected : List<Boolean> get() = _rawFilesSelected
    val selectionCount get() = _selectionCount

    fun selectionIndices() = AESFileManagerSelectionIndices(
        aesDirectories = aesDirectoriesSelected,
        aesFiles = aesFilesSelected,
        rawDirectories = rawDirectoriesSelected,
        rawFiles = rawFilesSelected,
        selectedCount = selectionCount,
        filesCount = allCount,
    )

    fun selectAll() {

        for (i in _aesDirectoriesSelected.indices)
            _aesDirectoriesSelected[i] = true
        for (i in _aesFilesSelected.indices)
            _aesFilesSelected[i] = true
        for (i in _rawDirectoriesSelected.indices)
            _rawDirectoriesSelected[i] = true
        for (i in _rawFilesSelected.indices)
            _rawFilesSelected[i] = true

        _selectionCount = allCount
    }

    fun unSelectAll() {

        for (i in _aesDirectoriesSelected.indices)
            _aesDirectoriesSelected[i] = false
        for (i in _aesFilesSelected.indices)
            _aesFilesSelected[i] = false
        for (i in _rawDirectoriesSelected.indices)
            _rawDirectoriesSelected[i] = false
        for (i in _rawFilesSelected.indices)
            _rawFilesSelected[i] = false

        _selectionCount = 0
    }

    fun switch(
        event: AESFileManagerEvent.TemporarySelection.Switch,
    ) {

        val list = if (event.isEncrypted)
            if (event.isDirectory)
                _aesDirectoriesSelected
            else
                _aesFilesSelected
        else
            if (event.isDirectory)
                _rawDirectoriesSelected
            else
                _rawFilesSelected

        val newValue = !list[event.index]

        list[event.index] = newValue

        if (newValue)
            _selectionCount++
        else
            _selectionCount--
    }

    fun clear() {

        _aesDirectoriesSelected.clear()
        _aesFilesSelected.clear()
        _rawDirectoriesSelected.clear()
        _rawFilesSelected.clear()

        allCount = 0
        _selectionCount = 0
    }

    fun init(content: AESContent) {

        for (i in content.aes.directories.indices)
            _aesDirectoriesSelected.add(false)

        for (i in content.aes.files.indices)
            _aesFilesSelected.add(false)

        for (i in content.raw.directories.indices)
            _rawDirectoriesSelected.add(false)

        for (i in content.raw.files.indices)
            _rawFilesSelected.add(false)

        allCount = content.aes.directories.size +
                content.aes.files.size +
                content.raw.directories.size +
                content.raw.files.size
    }

    fun select(indices: AESContentDataIndices) {

        for (i in indices.aes.directories)
            _aesDirectoriesSelected[i] = true

        for (i in indices.aes.files)
            _aesFilesSelected[i] = true

        for (i in indices.raw.directories)
            _rawDirectoriesSelected[i] = true

        for (i in indices.aes.files)
            _rawFilesSelected[i] = true
    }

    private var allCount = 0
}