package com.dicoding.cektandur.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.cektandur.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val repository: PlantRepository) : ViewModel() {

    fun getAllPlants() = liveData(Dispatchers.IO) {
        Log.d("DetailViewModel", "getAllPlants called")
        try {
            val response = repository.getAllPlants()
            Log.d("DetailViewModel", "Data received: ${response.plants?.size} plants")
            emit(response.plants)
        } catch (e: Exception) {
            Log.e("DetailViewModel", "Failed to fetch data", e)
            emit(emptyList())
        }
    }
}