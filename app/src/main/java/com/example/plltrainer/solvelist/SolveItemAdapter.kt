package com.example.plltrainer.solvelist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.plltrainer.R
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.pllsolve.Solve
import java.text.SimpleDateFormat
import java.util.*

class SolveItemAdapter (private val dataset: MutableList<Solve>): RecyclerView.Adapter<SolveItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val solveTime: TextView = view.findViewById(R.id.solve_time_text_view)
        val case: TextView = view.findViewById(R.id.pll_case_test_view)
        val deleteButton: TextView = view.findViewById(R.id.delete_solve_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.solve_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val item = dataset[position]
        holder.solveTime.text = "${item.time}"
        holder.case.text = "${item.pllCase}"
        holder.deleteButton.setOnClickListener {
            if(SolveDBService(holder.deleteButton.context).deleteSolve(item.ID)!=-1){
                dataset.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, dataset.size)
            }
        }
    }

    override fun getItemCount() = dataset.size
}

