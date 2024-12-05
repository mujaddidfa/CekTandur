package com.dicoding.cektandur.data.repository

import com.dicoding.cektandur.data.api.ApiService
import com.dicoding.cektandur.data.api.request.LoginRequest
import com.dicoding.cektandur.data.api.request.RegisterRequest
import com.dicoding.cektandur.data.api.response.LoginResponse
import com.dicoding.cektandur.data.api.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(email, password)
        return withContext(Dispatchers.IO) {
            apiService.login(loginRequest)
        }
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val registerRequest = RegisterRequest(name, email, password)
        return withContext(Dispatchers.IO) {
            apiService.register(registerRequest)
        }
    }
}