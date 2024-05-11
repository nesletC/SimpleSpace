package com.simplespace.android.lib.standard.io.file

import com.simplespace.android.lib.standard.io.file.FileType.JPG
import com.simplespace.android.lib.standard.io.file.FileType.MP3
import com.simplespace.android.lib.standard.io.file.FileType.MP4
import com.simplespace.android.lib.standard.io.file.FileType.PDF
import com.simplespace.android.lib.standard.io.file.FileType.PNG
import com.simplespace.android.lib.standard.io.file.FileType.SIMPLE_ENCRYPTED_DIRECTORY
import com.simplespace.android.lib.standard.io.file.FileType.SIMPLE_ENCRYPTED_FILE
import com.simplespace.android.lib.standard.io.file.FileType.SIMPLE_MESSAGE
import com.simplespace.android.lib.standard.io.file.FileType.SIMPLE_NOTE
import com.simplespace.android.lib.standard.io.file.FileType.SIMPLE_REMINDER
import com.simplespace.android.lib.standard.io.file.FileType.TXT
import com.simplespace.android.lib.standard.io.file.FileType.UNKNOWN
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.jpg
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.mp3
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.mp4
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.pdf
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.png
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.simpleEncryptedDirectory
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.simpleEncryptedFile
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.simpleMessage
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.simpleNote
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.simpleReminder
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.txt
import com.simplespace.android.lib.standard.io.file.FileTypes.Names.unknown


object FileTypes {

    fun name(type: FileType) =
        when (type) {
            MP4 -> mp4
            MP3 -> mp3
            PDF -> pdf
            TXT -> txt
            PNG -> png
            JPG -> jpg
            SIMPLE_ENCRYPTED_DIRECTORY -> simpleEncryptedDirectory
            SIMPLE_ENCRYPTED_FILE -> simpleEncryptedFile
            SIMPLE_MESSAGE -> simpleMessage
            SIMPLE_NOTE -> simpleNote
            SIMPLE_REMINDER -> simpleReminder
            UNKNOWN -> unknown
        }

    fun type(name: String) : FileType = when(name) {
        mp4 -> MP4
        mp3 -> MP3
        pdf -> PDF
        txt -> TXT
        png -> PNG
        jpg -> JPG
        simpleEncryptedDirectory -> SIMPLE_ENCRYPTED_DIRECTORY
        simpleEncryptedFile -> SIMPLE_ENCRYPTED_FILE
        simpleMessage -> SIMPLE_MESSAGE
        simpleNote -> SIMPLE_NOTE
        simpleReminder -> SIMPLE_REMINDER
        else -> UNKNOWN
    }

    private object Names {
        const val mp4 = "mp4"
        const val mp3 = "mp3"
        const val pdf = "pdf"
        const val txt = "txt"
        const val png = "png"
        const val jpg = "jpg"
        const val simpleEncryptedDirectory = "sed"
        const val simpleEncryptedFile = "sef"
        const val simpleMessage = "sme"
        const val simpleNote = "sno"
        const val simpleReminder = "sre"
        const val unknown = "unknown"
    }
}