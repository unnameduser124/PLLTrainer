package com.example.plltrainer.pllsolve

import kotlin.math.floor

class SolveList(var solveList: MutableList<Solve>) {

    //functions replaced with pulling data from database
    //fun addSolve(solve: Solve){
    //    if(solve.pllCase!=PLLCase.Error){
    //        solveList.add(solve)
    //        solveList.sortByDescending { it.ID }
    //    }
    //}

    //fun getNumberOfSolves(): Int {
    //    return solveList.size
    //}

    //fun getGlobalAverage(): Float {
    //    return getTotalTimeSeconds()/solveList.size.toFloat()
    //}

    //fun getTotalTime(): String{
    //    val totalTime = getTotalTimeSeconds()
    //    return if(totalTime <3600){
    //        "${totalTime/60}min"
    //    } else{
    //        val secondsLeft = totalTime%3600
    //        "${floor(totalTime / 3600.0).toInt()}h ${secondsLeft/60}min"
    //    }
    //}

    //fun getTotalTimeSeconds(): Long{
    //    var sum = 0L
    //    solveList.forEach {
    //        sum += (it.time*100).toLong()
    //    }
    //    return sum/100L
    //}


}