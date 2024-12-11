package com.dicoding.cektandur.ui.result

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.databinding.ActivityResultBinding
import com.dicoding.cektandur.di.Injection

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels {
        ResultViewModelFactory(Injection.providePlantRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prediction = intent.getIntExtra("PREDICTION", -1)
        val confidenceScore = intent.getFloatExtra("CONFIDENCE_SCORE", 0.0f)
        val imageUri = intent.getStringExtra("IMAGE_URI")

        if (prediction!= -1) {
            viewModel.getPlantById(prediction)
        }

        viewModel.plantItem.observe(this) { plantItem ->
            binding.tvDiseaseCause.text = plantItem.plant.diseaseName
            binding.penjelasanDisease.text = plantItem.plant.description
            binding.isiSolution.text = plantItem.plant.treatments.joinToString(", ")
            binding.isiProduct.text = plantItem.plant.alternativeProducts.joinToString(", ")
            binding.ivPlantDisease.setImageURI(Uri.parse(imageUri))
        }
    }
}