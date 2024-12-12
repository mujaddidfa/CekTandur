package com.dicoding.cektandur.ui.result

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cektandur.R
import com.dicoding.cektandur.data.api.request.HistoryRequest
import com.dicoding.cektandur.data.pref.UserPreferences
import com.dicoding.cektandur.databinding.ActivityResultBinding
import com.dicoding.cektandur.di.Injection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels {
        ResultViewModelFactory(Injection.providePlantRepository(), Injection.provideHistoryRepository())
    }
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences.getInstance(this)


        val prediction = intent.getIntExtra("PREDICTION", -1)
        val confidenceScore = intent.getFloatExtra("CONFIDENCE_SCORE", 0.0f)
        val imageUri = intent.getStringExtra("IMAGE_URI")
        val confidenceScoreInPercent = (confidenceScore * 100).toInt()

        if (prediction!= -1) {
            viewModel.getPlantById(prediction)
        }

        viewModel.plantItem.observe(this) { plantItem ->
            binding.tvPlantDisease.text = getString(R.string.plant_disease, plantItem.plant.diseaseName, confidenceScoreInPercent)
            binding.tvDescription.text = plantItem.plant.description
            binding.tvSolution.text = plantItem.plant.treatments.joinToString("\n - ", prefix = "\n - ")
            binding.ivPlant.setImageURI(Uri.parse(imageUri))
            binding.tvCauses.text = plantItem.plant.causes.joinToString("\n - ", prefix = "\n - ")

            val products = plantItem.plant.alternativeProducts.zip(plantItem.plant.alternativeProductsLinks)
            val adapter = ProductAdapter(this, products)
            binding.rvProductRecommendations.layoutManager = LinearLayoutManager(this)
            binding.rvProductRecommendations.adapter = adapter
        }

        binding.btnSave.setOnClickListener {
            saveHistory(confidenceScore)
        }
    }

    private fun saveHistory(confidenceScore: Float) {

        var className = ""
        var plantName = ""
        var analysisResult = ""

        viewModel.plantItem.observe(this) { plantItem ->
            className = plantItem.plant.className
            plantName = plantItem.plant.diseaseName
            analysisResult = plantItem.plant.description
        }

        lifecycleScope.launch {
            val userId = userPreferences.userId.first() ?: return@launch

            val historyRequest = HistoryRequest(
                userId = userId,
                className = className,
                diseaseName = plantName,
                analysisResult = analysisResult,
                confidence = confidenceScore
            )

            viewModel.addHistory(historyRequest)
        }
    }
}