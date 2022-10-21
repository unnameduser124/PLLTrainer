package com.example.plltrainer.pllsolve

import java.util.*

class SolveTimer(var startTime: Long = -1, var stopTime: Long = -1, var running: Boolean = false) {

    fun startTimer(){
        startTime = Calendar.getInstance().timeInMillis
        running = true
    }

    fun stopTimer(){
        stopTime = Calendar.getInstance().timeInMillis
        running = false
    }

    fun getCurrentTime(): String{
        var time = ((millisecondsToHundredth(Calendar.getInstance().timeInMillis) - millisecondsToHundredth(startTime))/100F).toString()
        if(time[time.length-2]=='.') {
            time+="0"
        }
        return time
    }

    fun millisecondsToSeconds(timeInMillis: Long): Int{
        return (timeInMillis/1000).toInt()
    }

    fun millisecondsToHundredth(timeInMillis: Long): Int{
        return (timeInMillis/10).toInt()
    }

    fun getFinalTime(): Float{
        return (millisecondsToHundredth(stopTime) - millisecondsToHundredth(startTime))/100F
    }

    fun getSolveObject(pllCase: PLLCase): Solve{
        if(stopTime != -1L){
            return Solve(getFinalTime(), pllCase, Calendar.getInstance())
        }
        return Solve(-1F, PLLCase.Error, Calendar.getInstance())
    }
}