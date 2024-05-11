package com.simplespace.android.lib.simple.fs.basic.directory

import com.simplespace.android.lib.simple.fs.basic.file.SimpleFile
import com.simplespace.android.lib.standard.io.file.MutableFileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.getMutableDirectoryContent
import com.simplespace.android.lib.standard.loop.loopN
import com.simplespace.android.lib.standard.storage.Storages
import java.io.File

abstract class SimpleFileMutableBase (
    final override var rootDirectoryIndex: Int,
    parentList: List<String> = listOf(),
    override var name: String = "",
) : SimpleMutableFile {

    override var rootDirectoryPath: String = Storages.path(rootDirectoryIndex)

    override var parentList = parentList.toMutableList()

    override val file get() = _file?: File(absoluteFullPath()).also { _file = it }

    override val content get() =
        _content?:file.getMutableDirectoryContent().also { _content = it }

    override fun getMutableContent() = content.let{
        MutableFileNamesWithDirectoryPartition(
            directories = it.directories, files = it.files
        )
    }

    override fun isEmpty(): Boolean = _content?.let {
        it.directories.isEmpty() && it.files.isEmpty()
    }?:file.list()!!.isEmpty()

    private var _file: File? = null

    private var _mutableContent: MutableFileNamesWithDirectoryPartition? = null

    private var _content : MutableFileNamesWithDirectoryPartition? = null

    override fun changeName(newName: String) {
        name = newName
    }

    override fun parent(level: Int) {

        loopN(level) {
            parentList.removeLast()
        }

        name = if (parentList.size == 0) "" else parentList.removeLast()

        onNavigate()
    }

    override fun child(childName: String) {

        if (name.isNotBlank())
            parentList.add(name)

        name = childName

        onNavigate()
    }

    override fun openRoot() {

        parentList.clear()
        name = ""

        onNavigate()
    }

    override fun openRoot(rootDirectoryPath: String, rootDirectoryIndex: Int) {

        this.rootDirectoryPath = rootDirectoryPath
        this.rootDirectoryIndex = rootDirectoryIndex

        openRoot()
    }

    override fun open(file: SimpleFile) {

        rootDirectoryIndex = file.rootDirectoryIndex
        rootDirectoryPath = Storages.path(rootDirectoryIndex)

        parentList = file.parentList.toMutableList()

        name = file.name

        onNavigate()
    }

    private fun onNavigate() {
        _file = null
        _content = null
    }
}