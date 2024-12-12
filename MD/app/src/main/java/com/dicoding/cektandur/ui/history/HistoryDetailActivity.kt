package com.dicoding.cektandur.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.R
import com.dicoding.cektandur.databinding.ActivityHistoryDetailBinding

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diseaseName = intent.getStringExtra("diseaseName")
        val timestamp = intent.getStringExtra("timestamp")
        val confidence = intent.getStringExtra("confidence")
        val analysisResult = intent.getStringExtra("analysisResult")
        val confidenceInPercent = (confidence?.toFloat()?.times(100))?.toInt()

        binding.tvPlantDisease.text = getString(R.string.plant_disease, diseaseName, confidenceInPercent)
        binding.tvDescription.text = analysisResult
        binding.tvTimestap.text = getString(R.string.analysis_date, timestamp)
    }
}