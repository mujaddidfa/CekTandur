package com.dicoding.cektandur.ui.home

import android.content.Intent
import android.util.Log
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
        Log.d("PlantAdapter", "Binding item: $name at position $position")
        holder.tvPlantName.text = name
        holder.ivPlantImage.setImageResource(image)
        val context = holder.itemView.context
        val backgroundArray = context.resources.obtainTypedArray(R.array.background)
        val backgroundResId = backgroundArray.getResourceId(position, -1)

        // Set the background of the itemView
        holder.itemView.setBackgroundResource(backgroundResId)

        // Cleanup TypedArray to prevent memory leaks
        backgroundArray.recycle()

        holder.itemView.setOnClickListener {
            Log.d("PlantAdapter", "Clicked on item: $name at position $position")

            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("PLANT_NAME", name)
            intent.putExtra("PLANT_DESCRIPTION", context.resources.getStringArray(R.array.plant_description)[position])
            intent.putExtra("PLANT_COVER", context.resources.obtainTypedArray(R.array.image_cover).getResourceId(position, -1))
            intent.putExtra("PLANT_ID", position + 1)
            context.startActivity(intent)
        }
    }

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val ivPlantImage: ImageView = itemView.findViewById(R.id.iv_plant_image)
    }
}