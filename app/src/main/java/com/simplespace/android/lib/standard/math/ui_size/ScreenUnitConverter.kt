package com.simplespace.android.lib.standard.math.ui_size

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlin.math.round

@Immutable
class ScreenUnitConverter(
    density: Density
) {

    private val aMillion = 1000000/* 1'000'000*/.toFloat()


    private val dpInAMillionPx = round(with(density) { aMillion.toDp().value })         // amount of dp in a million px
    private val spInAMillionPx = round(with(density) { aMillion.toSp().value })         // amount of sp in a million px

    private val spInAMillionDp = round(with(density) { aMillion.dp.toSp().value })      // amount of sp in a million dp


    private val pxToDP = dpInAMillionPx / aMillion
    private val  pxToSP = spInAMillionPx / aMillion

    private val  dpToSP = spInAMillionDp / aMillion

    private val  dpToPX = round( aMillion * aMillion / dpInAMillionPx ) / aMillion
    private val  spToPX = round( aMillion * aMillion / spInAMillionPx ) / aMillion

    val  spToDP = round( aMillion * aMillion / spInAMillionDp ) / aMillion

    private val inchToDP = 160.toFloat()

    fun convert(input: Float, conversion: ScreenUnitConversion) : Float =
        input * when (conversion) {
            ScreenUnitConversion.PX_TO_DP -> pxToDP
            ScreenUnitConversion.PX_TO_SP -> pxToSP
            ScreenUnitConversion.DP_TO_SP -> dpToSP
            ScreenUnitConversion.DP_TO_PX -> dpToPX
            ScreenUnitConversion.SP_TO_PX -> spToPX
            ScreenUnitConversion.SP_TO_DP -> spToDP
            ScreenUnitConversion.IN_TO_DP -> inchToDP
            ScreenUnitConversion.DP_TO_IN -> 1.toFloat() / inchToDP
            ScreenUnitConversion.IN_TO_PX -> dpToPX * inchToDP
            ScreenUnitConversion.PX_TO_IN -> pxToDP / inchToDP
            ScreenUnitConversion.IN_TO_SP -> dpToSP * inchToDP
            ScreenUnitConversion.SP_TO_IN -> spToDP / inchToDP
        }

    fun convert(input: Int, conversion: ScreenUnitConversion) : Float = convert(input.toFloat(), conversion)
}