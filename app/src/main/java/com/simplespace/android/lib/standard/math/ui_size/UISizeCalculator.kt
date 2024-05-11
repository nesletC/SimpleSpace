package com.simplespace.android.lib.standard.math.ui_size

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class UISizeCalculator (

    private val converter: ScreenUnitConverter,
    val maxPX: Float

) {

    val maxDP = maxPX.toDp()
    val maxSP = maxPX.toSp()


    val dp: Dp get() = px.toDp()

    val sp : TextUnit get() = px.toSp()

    val inch: Int get() = px.toInch()

    private var _px = 0f

    val px : Float get() {
        val px = _px
        _px = 0f
        return px
    }

    fun minPX(value: Float) = px(value)
    fun minPX(value: Int) = px(value)
    fun minPX(value: Double) = px(value)

    fun px(value: Float) : UISizeCalculator {
        if (_px < value) _px = value
        return this
    }
    fun px(value: Int) = px(value.toFloat())
    fun px(value: Double) = px(value.toFloat())
    fun maxPX(value: Float) : UISizeCalculator {
        if (_px > value) _px = value
        return this
    }
    fun maxPX(value: Int) : UISizeCalculator = maxPX(value.toFloat())
    fun maxPX(value: Double) : UISizeCalculator = maxPX(value.toFloat())

    private fun percentageToPX(value: Float) = (maxPX * value) / 100
    private fun percentageToPX(value: Int) = (maxPX * value) / 100
    private fun percentageToPX(value: Double) = (maxPX * value) / 100


    fun minPercentage(value: Float) = percentage(value)
    fun minPercentage(value: Int) = percentage(value)
    fun minPercentage(value: Double) = percentage(value)
    fun percentage(value: Float) = px(percentageToPX(value))
    fun percentage(value: Int) = px(percentageToPX(value))
    fun percentage(value: Double) = px(percentageToPX(value))
    fun maxPercentage(value: Float) = maxPX(percentageToPX(value))
    fun maxPercentage(value: Int) = maxPX(percentageToPX(value))
    fun maxPercentage(value: Double) = maxPX(percentageToPX(value))


    private fun Float.ofDP() =
        converter.convert(this, ScreenUnitConversion.DP_TO_PX)

    fun minDP (value: Float) = dp(value)
    fun minDP (value: Int) = dp(value)
    fun minDP (value: Double) = dp(value)
    fun minDP(value: Dp) = minDP(value.value)

    fun dp (value: Float) = px(value.ofDP())
    fun dp (value: Int) = dp(value.toFloat())
    fun dp (value: Double) = dp(value.toFloat())
    fun dp(value: Dp) = dp(value.value)
    fun maxDP (value: Float) = maxPX(value.ofDP())
    fun maxDP (value: Int) = maxDP(value.toFloat())
    fun maxDP (value: Double) = maxDP(value.toFloat())
    fun maxDP(value: Dp) = maxDP(value.value)


    private fun Float.ofSP() =
        converter.convert(this, ScreenUnitConversion.SP_TO_PX)

    fun minSP (value: Float) = sp(value)
    fun minSP (value: Int) = sp(value)
    fun minSP (value: Double) = sp(value)
    fun minSP(value: TextUnit) = minSP(value.value)

    fun sp (value: Float) = px(value.ofSP())
    fun sp (value: Int) = sp(value.toFloat())
    fun sp (value: Double) = sp(value.toFloat())
    fun sp (value: TextUnit) = sp(value.value)
    fun maxSP(value: Float) = maxPX(value.ofSP())
    fun maxSP (value: Int) = maxSP(value.toFloat())
    fun maxSP (value: Double) = maxSP(value.toFloat())
    fun maxSP (value: TextUnit) = maxSP(value.value)


    private fun Float.ofInch() =
        converter.convert(this, ScreenUnitConversion.IN_TO_PX)

    fun minInch (value: Float) = inch(value)
    fun minInch (value: Int) = inch(value)
    fun minInch (value: Double) = inch(value)

    fun inch (value: Float) = px(value.ofInch())
    fun inch (value: Int) = inch(value.toFloat())
    fun inch (value: Double) = inch(value.toFloat())
    fun maxInch(value: Float) = maxPX(value.ofInch())
    fun maxInch (value: Int) = maxInch(value.toFloat())
    fun maxInch (value: Double) = maxInch(value.toFloat())


    private fun Float.toDp() =
        converter.convert(this, ScreenUnitConversion.PX_TO_DP).dp
    private fun Float.toSp() =
        converter.convert(this, ScreenUnitConversion.PX_TO_SP).sp
    private fun Float.toInch() =
        converter.convert(this, ScreenUnitConversion.PX_TO_IN).toInt()
}