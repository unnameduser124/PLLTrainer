package com.example.plltrainer.global

fun roundFloat(roundedValue: Float, multiplier: Int): Float{
    return (roundedValue*multiplier).toInt()/multiplier.toFloat()
}
fun roundDouble(roundedValue: Double, multiplier: Int): Double{
    return (roundedValue*multiplier).toInt()/multiplier.toDouble()
}
