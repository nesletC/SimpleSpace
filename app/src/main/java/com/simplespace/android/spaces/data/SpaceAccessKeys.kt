package com.simplespace.android.spaces.data

import com.simplespace.android.lib.simple.characters.SimpleCharSet16
import com.simplespace.android.lib.simple.characters.SimpleCharSet64
import com.simplespace.android.lib.simple.security.SimpleEncryption.getSecretKey
import com.simplespace.android.lib.standard.array.init
import com.simplespace.android.lib.standard.number.BytesInts16
import com.simplespace.android.lib.standard.number.BytesInts64
import com.simplespace.android.lib.standard.number.BytesInts64PrefixedOverflow
import com.simplespace.android.lib.standard.number.StringInts
import com.simplespace.android.lib.standard.string.StringBytes
import com.simplespace.android.spaces.model.SpaceAccess
import kotlin.random.Random

object SpaceAccessKeys {

    const val keyString64Length = 64
    const val keyString16Length = 96

    const val directoryKeyString64Length = 43
    const val directoryKeyString16Length = 64

    fun directoryKeyToString64(key: ByteArray) = stringBytes64.string(key)

    fun directoryKeyToString16(key: ByteArray) = stringBytes16.string(key)

    fun directoryKeyToString(key: ByteArray, isFormat64: Boolean = false) =
        if (isFormat64)
            directoryKeyToString64(key)
        else
            directoryKeyToString16(key)

    fun validatedDirectoryKey(keyString: String) : ByteArray? =

        if (keyString.length == directoryKeyString64Length) {

            val intArray = stringInts64(keyString)

            if (intArray.indexOf(-1) == -1)
                BytesInts64PrefixedOverflow.bytes(intArray)
            else
                null
        }
        else if (keyString.length == directoryKeyString16Length) {

            val intArray = stringInts16(keyString)

            if (intArray.indexOf(-1) == -1)
                BytesInts16.bytes(intArray)
            else
                null
        }
        else
            null


    fun getAccess(validatedKeyString: String) : SpaceAccess {

        // get 33 bytes from the string

        val overloadedKey =
            BytesInts64.bytes(stringInts64(validatedKeyString.substring(0, 44)))

        // get the last of the 33 bytes; this byte belongs to the filename, not the key

        val unloadedByte = overloadedKey.last().toUByte().toInt()

        return SpaceAccess(
            fileName = CharArray(24).init { set ->
                set(
                    keyCharSet64[unloadedByte and 3]
                )
                set(
                    keyCharSet64[unloadedByte.shr(2)]
                )
                for (char in validatedKeyString.substring(42))
                    set(char)
            }
                .concatToString(),
            key = overloadedKey.copyOfRange(0, 32).getSecretKey()
        )
    }


    // will always convert a format16 key to a format64 key
    // so the access file name can be extracted right away

    fun validatedKeyString(keyString: String) : String? {

        if (keyString.length == keyString64Length) {
            return if (stringInts64(keyString).indexOf(-1) == -1) keyString else null
        }
        else if (keyString.length == keyString16Length) {

            val asIntArray = stringInts16(keyString)

            return if (asIntArray.indexOf(-1) == -1)
                stringBytes64.string(BytesInts16.bytes(asIntArray))
            else null
        }

        return null
    }

    fun randomKeyString64() : String = stringBytes64.string(randomKey())

    fun randomKeyString16() : String = stringBytes16.string(randomKey())

    fun randomKey() : ByteArray = Random.nextBytes(keyLength)


    private const val keyCharSet64 = SimpleCharSet64.default
    private const val keyCharSet16 = SimpleCharSet16.default

    private val stringInts64 = StringInts(keyCharSet64)
    private val stringInts16 = StringInts(keyCharSet16)

    private val stringBytes64 = StringBytes(
        BytesInts64PrefixedOverflow,
        stringInts64
    )

    private val stringBytes16 = StringBytes(
        BytesInts16,
        stringInts16
    )

    private const val keyLength = 48
}