package com.dicoding.cektandur.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.R
import com.dicoding.cektandur.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prediction = intent.getStringExtra("PREDICTION")
        val confidenceScore = intent.getFloatExtra("CONFIDENCE_SCORE", 0.0f)
        val confidenceScoreInPercent = (confidenceScore * 100).toInt()
        val imageUri = intent.getStringExtra("IMAGE_URI")

        binding.tvResult.text = getString(R.string.result_text, prediction, confidenceScoreInPercent)
        binding.ivResult.setImageURI(Uri.parse(imageUri))
    }
}