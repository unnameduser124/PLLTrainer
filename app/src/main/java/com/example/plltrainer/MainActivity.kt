package com.example.plltrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.databinding.ActivityMainBinding
import com.example.plltrainer.global.*
import com.example.plltrainer.pllsolve.PLLCase
import com.example.plltrainer.pllsolve.SolveTimer
import com.example.plltrainer.solvelist.SolveListActivity
import com.example.plltrainer.stats.CaseStatsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: com.example.plltrainer.databinding.ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val solveTimer = SolveTimer()
        var scramble: PLLCase

        if(onAppStart){
            valuesList.remove(PLLCase.Error)
            valuesList.shuffle()
            scrambleIterator = valuesList.listIterator()
            scramble = scrambleIterator.next()
            onAppStart = false
        }

        if(scrambleIterator.hasPrevious()){
            scramble = scrambleIterator.previous()
            scramble = scrambleIterator.next()
        }
        else {
            scramble = scrambleIterator.next()
            scramble = scrambleIterator.previous()
        }


        binding.caseSetupTextView.text = scramble.setup
        binding.numberOfSolves.text = "${SolveDBService(this).getNumberOfSolves()}"
        binding.totalTime.text = "${SolveDBService(this).getTotalTime()} min"
        binding.globalAverage.text = "${roundDouble(SolveDBService(this).getGlobalAverage(), 100)}"

        binding.timerActivationTextView.setOnClickListener {
            if (solveTimer.running) {
                solveTimer.stopTimer()
                binding.timerTextView.text = "${solveTimer.getFinalTime()}"
                val solve = solveTimer.getSolveObject(scramble)
                SolveDBService(it.context).saveSolveToDB(solve)
                updateStats()

                if(scrambleIterator.hasNext()){
                    scramble = scrambleIterator.next()
                }
                else{
                    valuesList.shuffle()
                    scrambleIterator = valuesList.listIterator()
                    scramble = scrambleIterator.next()
                }
                binding.caseSetupTextView.text = scramble.setup

            }
            else {
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

        binding.solvesList.setOnClickListener {
            val intent = Intent(this, SolveListActivity::class.java)
            startActivity(intent)
        }

        binding.perCaseStats.setOnClickListener{
            val intent = Intent(this, CaseStatsActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateStats() {
        binding.numberOfSolves.text = "${binding.numberOfSolves.text.toString().toInt()+1}"
        binding.totalTime.text = "${SolveDBService(this).getTotalTime()} min"
        binding.globalAverage.text = "${roundDouble(SolveDBService(this).getGlobalAverage(), 100)}"
    }
}