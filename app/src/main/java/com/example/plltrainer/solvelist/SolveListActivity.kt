package com.example.plltrainer.solvelist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plltrainer.MainActivity
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.databinding.SolveListActivityLayoutBinding
import com.example.plltrainer.global.solves

class SolveListActivity: AppCompatActivity() {
    private lateinit var binding: SolveListActivityLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SolveListActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listOfSolves = SolveDBService(this).loadAllSolvesFromDB()
        solves.solveList = listOfSolves

        val adapter = SolveItemAdapter(solves.solveList)
        binding.solveListRecyclerView.adapter = adapter
        binding.solveListRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.solveListRecyclerView.layoutManager = linearLayoutManager

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}