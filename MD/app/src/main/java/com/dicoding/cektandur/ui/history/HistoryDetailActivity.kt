package com.dicoding.cektandur.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.cektandur.R
import com.dicoding.cektandur.databinding.ActivityHistoryDetailBinding
import com.dicoding.cektandur.ui.result.ProductAdapter

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diseaseName = intent.getStringExtra("diseaseName")
        val timestamp = intent.getStringExtra("timestamp")
        val confidence = intent.getStringExtra("confidence")
        val description = intent.getStringExtra("description")
        val causes = intent.getStringExtra("causes")
        val treatments = intent.getStringExtra("treatments")
        val alternativeProducts = intent.getStringExtra("alternativeProducts")
        val alternativeProductsLinks = intent.getStringExtra("alternativeProductsLinks")
        val imageUrl = intent.getStringExtra("imageUrl")
        val confidenceInPercent = (confidence?.toFloat()?.times(100))?.toInt()

        binding.tvPlantDisease.text = getString(R.string.plant_disease, diseaseName, confidenceInPercent)
        binding.tvDescription.text = description
        binding.tvTimestap.text = getString(R.string.analysis_date, timestamp)
        binding.tvCauses.text = causes
        binding.tvSolution.text = treatments

        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivPlant)

        val products = alternativeProducts?.split("\n - ")?.filter { it.isNotEmpty() }
        val productLinks = alternativeProductsLinks?.split("\n - ")?.filter { it.isNotEmpty() }
        val productPairs = products?.zip(productLinks ?: emptyList()) ?: emptyList()

        val adapter = ProductAdapter(this, productPairs)
        binding.rvProductRecommendations.layoutManager = LinearLayoutManager(this)
        binding.rvProductRecommendations.adapter = adapter
    }
}