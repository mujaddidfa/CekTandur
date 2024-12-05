package com.dicoding.cektandur.di

import android.content.Context
import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.data.repository.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository(apiService)
    }
}