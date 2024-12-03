package com.dicoding.cektandur.data.api

import com.dicoding.cektandur.data.api.request.LoginRequest
import com.dicoding.cektandur.data.api.request.RegisterRequest
import com.dicoding.cektandur.data.api.response.LoginResponse
import com.dicoding.cektandur.data.api.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}