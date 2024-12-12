package com.dicoding.cektandur.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.R
import com.dicoding.cektandur.data.api.response.PlantsItem

class DiseaseAdapter(private val diseaseList: List<PlantsItem>) : RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_disease, parent, false)
        return DiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseaseList[position]
        holder.tvDiseaseName.text = disease.diseaseName
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DiseaseDetailActivity::class.java)
            intent.putExtra("DISEASE_DATA", disease)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = diseaseList.size

    class DiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDiseaseName: TextView = itemView.findViewById(R.id.tv_disease_name)
    }
}