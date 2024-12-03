package com.dicoding.cektandur.data.repository

import com.dicoding.cektandur.data.api.ApiService
import com.dicoding.cektandur.data.api.request.RegisterRequest
import com.dicoding.cektandur.data.api.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository(private val apiService: ApiService) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val registerRequest = RegisterRequest(name, email, password)
        return withContext(Dispatchers.IO) {
            apiService.register(registerRequest)
        }
    }
}