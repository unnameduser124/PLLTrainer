package com.example.plltrainer.global

fun roundFloat(roundedValue: Float, multiplier: Int): Float{
    return ((roundedValue*multiplier).toInt()/multiplier.toFloat())
}