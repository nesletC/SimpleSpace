package com.simplespace.android.lib.standard.dataSize

import com.simplespace.android.lib.standard.dataSize.DataSizeUnit.BYTE
import com.simplespace.android.lib.standard.dataSize.DataSizeUnit.GIGA_BYTE
import com.simplespace.android.lib.standard.dataSize.DataSizeUnit.KILO_BYTE
import com.simplespace.android.lib.standard.dataSize.DataSizeUnit.MEGA_BYTE
import com.simplespace.android.lib.standard.iterable.map.inverted

object DataSizes {

    val byte get() = size[BYTE]!!
    const val short = 2
    const val int = 4
    const val long = 8
    val kb get() = size[KILO_BYTE]!!
    val mb get() = size[MEGA_BYTE]!!
    val gb get() = size[GIGA_BYTE]!!
    const val tb = 1099511627776    // this is of type Long

    const val tiny = 256

    val unit: Map<Int, DataSizeUnit> = mapOf(
        1 to BYTE,
        1024 to KILO_BYTE,
        1048576 to MEGA_BYTE,
        1073741824 to GIGA_BYTE,
    )

    val size = unit.inverted()


    //only works for positive values

    fun get(byteCount: Long) : DataSize {

        var count = byteCount
        var countUnit = 0
        while (count > kb && countUnit < 3) {
            countUnit++
            count = count shr(10)
        }
        return DataSize (
            unit[countUnit]!!,
            count.toInt(),
        )
    }
}