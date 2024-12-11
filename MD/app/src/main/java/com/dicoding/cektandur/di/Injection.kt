package com.dicoding.cektandur.di

import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.data.repository.AuthRepository
import com.dicoding.cektandur.data.repository.PlantRepository

object Injection {
    fun provideAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }

    fun providePlantRepository(): PlantRepository {
        val apiService = ApiConfig.getApiService()
        return PlantRepository(apiService)
    }
}