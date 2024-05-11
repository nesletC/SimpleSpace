package com.simplespace.android.lib.simple.fs.basic.directory

import com.simplespace.android.lib.standard.io.file.AlternativeFileName
import com.simplespace.android.lib.standard.io.file.FileNamesWithDirectoryPartition
import com.simplespace.android.lib.standard.io.file.move

object SimpleDirectory {

    inline fun <T>SimpleMutableFile.onChild(childFileName: String, action: () -> T) : T {

        child(childFileName)

        val result = action()

        parent()

        return result
    }

    fun SimpleMutableFile.moveRecursively(
        target: SimpleMutableFile,
        overwrite: Boolean = false,
        copy: Boolean = false,
        selection: FileNamesWithDirectoryPartition = content,
    ) {

        fun moveFile(
            childFileName: String,
            targetFileName: String,
            fileExists: Boolean
        ) {

            onChild(childFileName){
                target.child(targetFileName)

                file.move(target.file, copy, fileExists)

                target.parent()
            }
        }

        fun moveUniqueFile(
            childFileName: String,
        ) = moveFile(
            childFileName = childFileName,
            targetFileName = childFileName,
            fileExists = false
        )

        fun moveFile(
            childFileName: String,
        ) {
            val fileExists = target.content.files.contains(childFileName)

            if (fileExists)
                moveFile(
                    childFileName = childFileName,
                    targetFileName = if (overwrite)
                        childFileName
                    else
                        AlternativeFileName.file(childFileName, target.content.files),
                    fileExists = overwrite
                )
            else
                moveUniqueFile(
                    childFileName = childFileName,
                )
        }

        fun moveDirectory(
            childDirectoryName: String,
            targetDirectoryName: String,
            directoryExists: Boolean,
            recursion: () -> Unit
        ) {

            onChild(childDirectoryName){

                target.child(targetDirectoryName)

                if (!directoryExists)
                    target.file.mkdir()

                recursion()

                if (!copy)
                    file.delete()

                target.parent()

            }
        }

        fun moveUniqueDirectory(
            childDirectoryName: String,
            recursion: () -> Unit,
        ) = moveDirectory(
            childDirectoryName = childDirectoryName,
            targetDirectoryName = childDirectoryName,
            directoryExists = false,
            recursion = recursion
        )

        fun emptyDirectoryRecursion() {
            content.files.forEach{
                moveUniqueFile(it)
            }
            content.directories.forEach{
                moveUniqueDirectory(it, ::emptyDirectoryRecursion)
            }
        }

        fun moveDirectory(
            childDirectoryName: String,
            recursion: () -> Unit
        ) {

            val exists = target.content.directories.contains(childDirectoryName)

            if (exists)
                moveDirectory(
                    childDirectoryName = childDirectoryName,
                    directoryExists = overwrite,
                    targetDirectoryName = if(overwrite)
                        childDirectoryName
                    else
                        AlternativeFileName.directory(
                            childDirectoryName, target.content.directories
                        ),
                    recursion = if (overwrite)
                        recursion
                    else
                        ::emptyDirectoryRecursion
                )
            else
                moveUniqueDirectory(
                    childDirectoryName = childDirectoryName,
                    recursion = ::emptyDirectoryRecursion
                )
        }

        fun recursion() {

            content.files.forEach (::moveFile)

            content.directories.forEach{
                moveDirectory(it, ::recursion)
            }
        }


        selection.files.forEach(::moveFile)

        selection.directories.forEach{
            moveDirectory(it, ::recursion)
        }
    }
}