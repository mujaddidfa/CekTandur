package com.dicoding.cektandur.data.repository

import com.dicoding.cektandur.data.api.ApiService

class PlantRepository(private val apiService: ApiService) {
    suspend fun getPlantById(id: Int) = apiService.getPlantById(id)
    suspend fun getAllPlants() = apiService.getAllPlants()
}