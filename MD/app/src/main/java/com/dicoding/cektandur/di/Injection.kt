package com.dicoding.cektandur.di

import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.data.repository.AuthRepository

object Injection {
    fun provideAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }
}