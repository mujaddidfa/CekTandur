package com.dicoding.cektandur.data.repository

import android.util.Log
import com.dicoding.cektandur.data.api.ApiService

class PlantRepository(private val apiService: ApiService) {
    suspend fun getPlantById(id: Int) = apiService.getPlantById(id)
    suspend fun getAllPlants() = try {
        val response = apiService.getAllPlants()
        Log.d("PlantRepository", "API call successful: ${response.plants?.size} plants received")
        response
    } catch (e: Exception) {
        Log.e("PlantRepository", "API call failed", e)
        throw e
    }
}