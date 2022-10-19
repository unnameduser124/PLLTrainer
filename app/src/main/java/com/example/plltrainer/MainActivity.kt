package com.example.plltrainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.plltrainer.databinding.ActivityMainBinding
import com.example.plltrainer.pllsolve.SolveTimer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val solveTimer = SolveTimer()

       binding.timerActivationTextView.setOnClickListener{
           if(solveTimer.running){
               solveTimer.stopTimer()
               binding.timerTextView.text = "${solveTimer.getFinalTime()}"
           }
           else{
               solveTimer.startTimer()
               val handler = Handler(mainLooper)

               val runnableCode = object: Runnable {
                   override fun run() {
                        binding.timerTextView.text = "${solveTimer.getCurrentTime()}"
                        if(solveTimer.running){
                            handler.postDelayed(this, 10)
                        }
                   }
               }
               handler.post(runnableCode)
           }
       }
    }
}