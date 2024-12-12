package com.dicoding.cektandur.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.data.api.response.DataItem
import com.dicoding.cektandur.databinding.HistoryPlantBinding
import com.dicoding.cektandur.ui.history.HistoryDetailActivity
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private val historyList: List<DataItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: HistoryPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: DataItem) {
            binding.tvPlantDisease.text = history.diseaseName

            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val targetFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            val date = history.timestamp?.let { originalFormat.parse(it) }
            val formattedDate = date?.let { targetFormat.format(it) }

            binding.tvDate.text = formattedDate

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, HistoryDetailActivity::class.java).apply {
                    putExtra("diseaseName", history.diseaseName)
                    putExtra("timestamp", formattedDate)
                    putExtra("confidence", history.confidence)
                    putExtra("analysisResult", history.analysisResult)
                }
                context.startActivity(intent)
            }
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