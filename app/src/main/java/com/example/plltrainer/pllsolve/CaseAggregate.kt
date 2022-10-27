package com.example.plltrainer.pllsolve

data class CaseAggregate (val case: PLLCase, val mean: Double, val numberOfSolves: Int, var meanOf50: Double = 0.0)