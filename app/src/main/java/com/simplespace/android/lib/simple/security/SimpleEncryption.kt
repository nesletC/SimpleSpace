package com.simplespace.android.lib.simple.security

import android.security.keystore.KeyProperties
import com.simplespace.android.lib.standard.array.byteArray.getInt
import com.simplespace.android.lib.standard.array.byteArray.getUByte
import com.simplespace.android.lib.standard.chain.reader.BasicChainReader
import com.simplespace.android.lib.standard.chain.reader.read
import com.simplespace.android.lib.standard.chain.writer.BasicChainWriter
import com.simplespace.android.lib.standard.chain.writer.writeIt
import com.simplespace.android.lib.standard.dataSize.DataSizes
import com.simplespace.android.lib.standard.number.toByteArray
import java.io.File
import java.nio.ByteBuffer
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


private const val TRANSFORMATION =
    "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"

object SimpleEncryption {

    const val KEY_SIZE = 32

    fun ByteArray.sha256Hash() : ByteArray {

        val messageDigest = MessageDigest.getInstance("SHA_256")
        messageDigest.update(this, 0, size)

        return messageDigest.digest()

    }

    fun randomKey(): ByteArray = Random.nextBytes(KEY_SIZE)

    fun ByteArray.getSecretKey() : SecretKeySpec = SecretKeySpec(this, "AES")

    fun ByteArray.isValidSecretKey() = size == KEY_SIZE



    fun BasicChainWriter<ByteArray>.encrypt(key: SecretKeySpec, bytesToEncrypt: ByteArray) : Int =

        getCipher256().run {

            init(Cipher.ENCRYPT_MODE, key)


            val encryptedBytes = doFinal(bytesToEncrypt)
            val encryptedBytesSize = encryptedBytes.size.toByteArray()

            write(
                ByteBuffer
                    .allocate(1 + iv.size + encryptedBytesSize.size + encryptedBytes.size)
                    .put(iv.size.toByte())
                    .put(iv)
                    .put(encryptedBytesSize)
                    .put(encryptedBytes)
                    .array()
            )
        }

    fun BasicChainReader<ByteArray>.decrypt(key: SecretKeySpec) : ByteArray =

        getCipher256().run {

            init(
                Cipher.DECRYPT_MODE, key, IvParameterSpec(
                    read(read(1).getUByte().toInt())
                )
            )
            doFinal(read(read(4).getInt()))
        }



    fun File.encrypt(key: SecretKeySpec, target: File) {

        target.writeIt {

            read{

                while(isReadable)

                    it.encrypt( key = key, bytesToEncrypt = read(DataSizes.mb) )
            }
        }
    }

    fun File.decrypt(key: SecretKeySpec, target: File) {

        read{

            target.writeIt {

                while(isReadable) {

                    it.write( array = decrypt(key) )
                }
            }
        }
    }


    private fun getCipher256() : Cipher = Cipher.getInstance(TRANSFORMATION)
}