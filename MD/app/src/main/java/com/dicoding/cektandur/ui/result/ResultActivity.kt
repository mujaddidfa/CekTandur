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
import com.dicoding.cektandur.utils.uriToFile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
                saveHistory(confidenceScore, imageUri)
            }
        }.show()
    }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun saveHistory(confidenceScore: Float, imageUri: String?) {
        var className = ""
        var diseaseName = ""

        viewModel.plantItem.observe(this) { plantItem ->
            className = plantItem.plant.className
            diseaseName = plantItem.plant.diseaseName
        }

        lifecycleScope.launch {
            try {
                val userId = userPreferences.userId.first() ?: return@launch
                val file = uriToFile(Uri.parse(imageUri), this@ResultActivity)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val plantImage = MultipartBody.Part.createFormData("plantImage", file.name, requestImageFile)

                val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
                val classNameBody = className.toRequestBody("text/plain".toMediaTypeOrNull())
                val diseaseNameBody = diseaseName.toRequestBody("text/plain".toMediaTypeOrNull())
                val confidenceBody = confidenceScore.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("ResultActivity", "className: $className, diseaseName: $diseaseName")

                viewModel.addHistory(userIdBody, classNameBody, diseaseNameBody, confidenceBody, plantImage)
                Toast.makeText(this@ResultActivity, "Data successfully saved to history", Toast.LENGTH_SHORT).show()

            } catch (e: HttpException) {
                if (e.code() == 404) {
                    Log.e("ResultActivity", "Endpoint not found: ${e.message()}")
                    Toast.makeText(this@ResultActivity, "Failed to save history: Endpoint not found", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ResultActivity", "HTTP error: ${e.code()} - ${e.message()}")
                    Toast.makeText(this@ResultActivity, "Failed to save history: ${e.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ResultActivity", "Unexpected error: ${e.message}")
                Toast.makeText(this@ResultActivity, "An unexpected error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }
}