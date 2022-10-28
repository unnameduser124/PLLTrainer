package com.example.plltrainer.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plltrainer.R
import com.example.plltrainer.global.roundDouble
import com.example.plltrainer.pllsolve.CaseAggregate

class CaseItemAdapter (private val dataset: List<CaseAggregate>): RecyclerView.Adapter<CaseItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val globalMean: TextView = view.findViewById(R.id.global_mean_text_view)
        val case: TextView = view.findViewById(R.id.case_name_text_view)
        val meanOf50: TextView = view.findViewById(R.id.mean_of_50_text_view)
        val numberOfSolves: TextView = view.findViewById(R.id.case_number_of_solves)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.case_stats_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val item = dataset[position]
        holder.globalMean.text = "${roundDouble(item.mean, 100)}"
        holder.case.text = "${item.case}"
        holder.meanOf50.text = "${roundDouble(item.meanOf50, 100)}"
        holder.numberOfSolves.text = "${item.numberOfSolves}"
    }

    override fun getItemCount() = dataset.size
}