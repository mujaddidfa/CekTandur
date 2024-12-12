package com.dicoding.cektandur.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.data.api.response.DataItem
import com.dicoding.cektandur.databinding.HistoryPlantBinding

class HistoryAdapter(private val historyList: List<DataItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: HistoryPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: DataItem) {
            binding.namaPenyakit.text = history.diseaseName
            binding.date.text = history.timestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size
}