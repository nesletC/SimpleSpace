package com.simplespace.android.lib.simple.characters

import com.simplespace.android.lib.standard.number.StringInts

object SimpleCharSet64 {

    const val default = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@"

    operator fun invoke() = StringInts(default)
}