package com.dicoding.cektandur.di

import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.data.repository.LoginRepository
import com.dicoding.cektandur.data.repository.RegisterRepository

object Injection {
    fun provideRegisterRepository(): RegisterRepository {
        val apiService = ApiConfig.provideApiService()
        return RegisterRepository(apiService)
    }

    fun provideLoginRepository(): LoginRepository {
        val apiService = ApiConfig.provideApiService()
        return LoginRepository(apiService)
    }
}