package com.example.plltrainer.global

import com.example.plltrainer.pllsolve.PLLCase

val valuesList = PLLCase.values().toMutableList()
lateinit var scrambleIterator: MutableListIterator<PLLCase>
var onAppStart = true
