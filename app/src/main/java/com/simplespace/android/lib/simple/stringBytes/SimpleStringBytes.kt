package com.simplespace.android.lib.simple.stringBytes

import com.simplespace.android.lib.simple.characters.SimpleCharSet16
import com.simplespace.android.lib.simple.characters.SimpleCharSet64
import com.simplespace.android.lib.standard.number.BytesInts16
import com.simplespace.android.lib.standard.number.BytesInts64PrefixedOverflow
import com.simplespace.android.lib.standard.number.StringInts
import com.simplespace.android.lib.standard.string.StringBytes

object DefaultStringBytes {

    val format64 = StringBytes(
        BytesInts64PrefixedOverflow,
        StringInts(SimpleCharSet64.default)
    )

    val format16 = StringBytes(
        BytesInts16,
        StringInts(SimpleCharSet16.default)
    )
}