package com.dicoding.cektandur.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.cektandur.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val repository: PlantRepository) : ViewModel() {

    fun getAllPlants() = liveData(Dispatchers.IO) {
        try {
            val response = repository.getAllPlants()
            emit(response.plants)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}