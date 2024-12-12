package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.data.api.response.PlantsItem
import com.dicoding.cektandur.databinding.ActivityDiseaseDetailBinding

class DiseaseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val disease = intent.getParcelableExtra<PlantsItem>("DISEASE_DATA")
        disease?.let {
            binding.tvDiseaseName.text = it.diseaseName
            binding.tvDiseaseDescription.text = it.description
            binding.tvDiseaseCauses.text = it.causes?.joinToString("\n")
            binding.tvDiseaseTreatments.text = it.treatments?.joinToString("\n")
        }
    }
}