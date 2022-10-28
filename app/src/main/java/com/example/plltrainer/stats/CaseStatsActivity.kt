package com.example.plltrainer.stats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plltrainer.MainActivity
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.databinding.CaseStatsActivityLayoutBinding

class CaseStatsActivity: AppCompatActivity() {
    private lateinit var binding: CaseStatsActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CaseStatsActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val caseAggregateList = SolveDBService(this).caseAverageAndSolveNumber()
        val adapter = CaseItemAdapter(caseAggregateList)
        binding.caseStatsRecyclerView.adapter = adapter
        binding.caseStatsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.caseStatsRecyclerView.layoutManager = linearLayoutManager

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}