package com.simplespace.android.lib.standard.time

object Time {

    fun current() = System.currentTimeMillis()

    fun Long.convert(from: Unit, to: Unit) = convertFrom(this, from, to)

    fun convertFrom(amount: Long, from: Unit, to: Unit) : Long {

        if (from == to) return amount

        val fromIndex = entries.indexOf(from)
        val toIndex = entries.indexOf(to)

        var result = amount

        if (fromIndex > toIndex) {

            val relevantSteps = entries.subList(toIndex, fromIndex)

            relevantSteps.forEach{
                result *= conversionSteps[it]!!
            }
        }

        else if (toIndex > fromIndex) {

            val relevantSteps = entries.subList(fromIndex, toIndex)

            relevantSteps.forEach{
                result /= conversionSteps[it]!!
            }
        }

        return result
    }

    private val conversionSteps = mapOf(
        Unit.MILLI_SEC to 1000,
        Unit.SECOND to 60,
        Unit.MINUTE to 60,
        Unit.HOUR to 24,
        Unit.DAY to 7,
    )

    private val entries = Unit.entries

    enum class Unit {
        MILLI_SEC, SECOND, MINUTE, HOUR, DAY, WEEK
    }
}