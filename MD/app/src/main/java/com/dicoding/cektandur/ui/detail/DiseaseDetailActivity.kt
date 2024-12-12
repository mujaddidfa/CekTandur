package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cektandur.data.api.response.PlantsItem
import com.dicoding.cektandur.databinding.ActivityDiseaseDetailBinding
import com.dicoding.cektandur.ui.result.ProductAdapter

class DiseaseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val disease = intent.getParcelableExtra<PlantsItem>("DISEASE_DATA")
        disease?.let {
            binding.tvPlantDisease.text = it.diseaseName
            binding.tvDescription.text = it.description
            binding.tvSolution.text = it.treatments?.joinToString("\n - ", prefix = "\n - ") ?: ""
            binding.tvCauses.text = it.causes?.joinToString("\n - ", prefix = "\n - ") ?: ""

            val products = it.alternativeProducts?.filterNotNull()?.zip(it.alternativeProductsLinks?.filterNotNull() ?: emptyList())
            val adapter = products?.let { it1 -> ProductAdapter(this, it1) }
            binding.rvProductRecommendations.layoutManager = LinearLayoutManager(this)
            binding.rvProductRecommendations.adapter = adapter
        }
    }
}