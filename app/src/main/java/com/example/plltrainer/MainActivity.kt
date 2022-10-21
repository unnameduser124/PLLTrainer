package com.example.plltrainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.database.SolvesDB
import com.example.plltrainer.databinding.ActivityMainBinding
import com.example.plltrainer.global.roundFloat
import com.example.plltrainer.global.solves
import com.example.plltrainer.pllsolve.PLLCase
import com.example.plltrainer.pllsolve.SolveTimer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listOfSolves = SolveDBService(this).loadAllSolvesFromDB()
        solves.solveList = listOfSolves

        val solveTimer = SolveTimer()

        val valuesList = PLLCase.values().toMutableList()
        valuesList.remove(PLLCase.Error)
        var scramble = valuesList.random()
        binding.caseSetupTextView.text = scramble.setup

        updateStats()
        binding.timerActivationTextView.setOnClickListener {
            if (solveTimer.running) {
                solveTimer.stopTimer()
                binding.timerTextView.text = "${solveTimer.getFinalTime()}"
                val solve = solveTimer.getSolveObject(scramble)
                val newID = SolveDBService(it.context).saveSolveToDB(solve)
                if(newID>0){
                   solve.ID = newID
                }
                solves.addSolve(solve)
                updateStats()
                scramble = valuesList.random()
                binding.caseSetupTextView.text = scramble.setup
            } else {
                solveTimer.startTimer()
                val handler = Handler(mainLooper)

                val runnableCode = object : Runnable {
                    override fun run() {
                        if (solveTimer.running) {
                            val time = solveTimer.getCurrentTime()
                            binding.timerTextView.text = time
                            handler.postDelayed(this, 20)
                        }
                    }
                }
                handler.post(runnableCode)
            }
        }
    }

    private fun updateStats() {
        binding.numberOfSolves.text = "${solves.getNumberOfSolves()}"
        binding.totalTime.text = solves.getTotalTime()
        binding.globalAverage.text = "${roundFloat(solves.getGlobalAverage(), 100)}"
    }
}