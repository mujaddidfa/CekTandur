package com.dicoding.cektandur.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.R
import com.dicoding.cektandur.ui.detail.DetailActivity
import com.dicoding.cektandur.utils.Plant

class PlantAdapter(private val listPlant: ArrayList<Plant>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = listPlant.size

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val (name, image) = listPlant[position]
        holder.tvPlantName.text = name
        holder.ivPlantImage.setImageResource(image)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("PLANT_NAME", name)
            context.startActivity(intent)
        }
    }

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val ivPlantImage: ImageView = itemView.findViewById(R.id.iv_plant_image)
    }
}