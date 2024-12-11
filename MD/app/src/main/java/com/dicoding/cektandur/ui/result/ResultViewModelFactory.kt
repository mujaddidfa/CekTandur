package com.dicoding.cektandur.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.PlantRepository

class ResultViewModelFactory(private val plantRepository: PlantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}