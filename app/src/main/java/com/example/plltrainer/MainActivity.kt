package com.example.plltrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.databinding.ActivityMainBinding
import com.example.plltrainer.global.roundFloat
import com.example.plltrainer.global.solves
import com.example.plltrainer.pllsolve.PLLCase
import com.example.plltrainer.pllsolve.Solve
import com.example.plltrainer.pllsolve.SolveTimer
import com.example.plltrainer.solvelist.SolveListActivity
import com.example.plltrainer.stats.CaseStatsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val solveTimer = SolveTimer()

        val valuesList = PLLCase.values().toMutableList()
        valuesList.shuffle()
        valuesList.remove(PLLCase.Error)
        var iterator = valuesList.iterator()
        var scramble = iterator.next()
        binding.caseSetupTextView.text = scramble.setup

        binding.numberOfSolves.text = "${SolveDBService(this).getNumberOfSolves()}"
        binding.totalTime.text = "${SolveDBService(this).getTotalTime()} min"
        binding.globalAverage.text = "${roundFloat(SolveDBService(this).getGlobalAverage().toFloat(), 100)}"
        binding.timerActivationTextView.setOnClickListener {
            if (solveTimer.running) {
                solveTimer.stopTimer()
                binding.timerTextView.text = "${solveTimer.getFinalTime()}"
                val solve = solveTimer.getSolveObject(scramble)
                val newID = SolveDBService(it.context).saveSolveToDB(solve)
                if(newID>0){
                   solve.ID = newID
                }
                updateStats(solve)
                if(iterator.hasNext()){
                    scramble = iterator.next()
                }
                else{
                    valuesList.shuffle()
                    iterator = valuesList.iterator()
                }
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

        binding.solvesListButton.setOnClickListener {
            val intent = Intent(this, SolveListActivity::class.java)
            startActivity(intent)
        }
        binding.perCaseStatsButton.setOnClickListener{
            val intent = Intent(this, CaseStatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateStats(solve: Solve) {
        binding.numberOfSolves.text = "${binding.numberOfSolves.text.toString().toInt()+1}"
        binding.totalTime.text = "${SolveDBService(this).getTotalTime()} min"
        binding.globalAverage.text = "${roundFloat(SolveDBService(this).getGlobalAverage().toFloat(), 100)}"
    }
}