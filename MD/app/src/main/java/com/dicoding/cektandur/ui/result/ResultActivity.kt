package com.dicoding.cektandur.ui.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import retrofit2.HttpException

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels {
        ResultViewModelFactory(
            Injection.providePlantRepository(),
            Injection.provideHistoryRepository()
        )
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

        if (prediction != -1) {
            viewModel.getPlantById(prediction)
        }

        viewModel.plantItem.observe(this) { plantItem ->
            binding.tvPlantDisease.text = getString(
                R.string.plant_disease,
                plantItem.plant.diseaseName,
                confidenceScoreInPercent
            )
            binding.tvDescription.text = plantItem.plant.description
            binding.tvSolution.text =
                plantItem.plant.treatments.joinToString("\n - ", prefix = "\n - ")
            binding.ivPlant.setImageURI(Uri.parse(imageUri))
            binding.tvCauses.text = plantItem.plant.causes.joinToString("\n - ", prefix = "\n - ")

            val products =
                plantItem.plant.alternativeProducts.zip(plantItem.plant.alternativeProductsLinks)
            val adapter = ProductAdapter(this, products)
            binding.rvProductRecommendations.layoutManager = LinearLayoutManager(this)
            binding.rvProductRecommendations.adapter = adapter
        }

        binding.btnSave.setOnClickListener{
        AlertDialog.Builder(this).apply {
            setTitle("Berhasil!")
            setMessage("Tersimpan di history")
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                saveHistory(confidenceScore)
            }
        }.show()
    }
        binding.btnBack.setOnClickListener {
            // This will finish the current activity and return to the previous fragment (ScanFragment)
            finish()
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
            try {
                val userId = userPreferences.userId.first() ?: return@launch

                val historyRequest = HistoryRequest(
                    userId = userId,
                    className = className,
                    diseaseName = plantName,
                    analysisResult = analysisResult,
                    confidence = confidenceScore
                )

                viewModel.addHistory(historyRequest)
                Toast.makeText(this@ResultActivity, "Data successfully saved to history", Toast.LENGTH_SHORT).show()

            } catch (e: HttpException) {
                Log.e("ResultActivity", "HTTP error: ${e.code()} - ${e.message()}")
                Toast.makeText(this@ResultActivity, "Failed to save history: ${e.message()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("ResultActivity", "Unexpected error: ${e.message}")
                Toast.makeText(this@ResultActivity, "An unexpected error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
}