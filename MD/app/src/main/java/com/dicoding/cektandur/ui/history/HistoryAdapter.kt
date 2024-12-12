package com.dicoding.cektandur.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.cektandur.data.api.response.DataItem
import com.dicoding.cektandur.databinding.HistoryPlantBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private val historyList: List<DataItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: HistoryPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: DataItem) {
            binding.tvPlantDisease.text = history.diseaseName

            val originalFormat = SimpleDateFormat("MM/dd/yyyy, h:mm:ss a", Locale.getDefault())
            val targetFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            val date = history.timestamp?.let { originalFormat.parse(it) }
            val formattedDate = date?.let { targetFormat.format(it) }

            binding.tvDate.text = formattedDate

            Glide.with(binding.root.context)
                .load(history.imageUrl)
                .into(binding.ivHistory)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, HistoryDetailActivity::class.java).apply {
                    putExtra("diseaseName", history.diseaseName)
                    putExtra("timestamp", formattedDate)
                    putExtra("confidence", history.confidence)
                    putExtra("description", history.description)
                    putExtra("causes", history.causes?.joinToString("\n - ", prefix = "\n - "))
                    putExtra("treatments", history.treatments?.joinToString("\n - ", prefix = "\n - "))
                    putExtra("alternativeProducts", history.alternativeProducts?.joinToString("\n - ", prefix = "\n - "))
                    putExtra("alternativeProductsLinks", history.alternativeProductsLinks?.joinToString("\n - ", prefix = "\n - "))
                    putExtra("imageUrl", history.imageUrl)
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