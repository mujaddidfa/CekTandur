package com.dicoding.cektandur.data.api

import com.dicoding.cektandur.data.api.request.LoginRequest
import com.dicoding.cektandur.data.api.request.RegisterRequest
import com.dicoding.cektandur.data.api.response.LoginResponse
import com.dicoding.cektandur.data.api.response.PlantItemResponse
import com.dicoding.cektandur.data.api.response.PlantResponse
import com.dicoding.cektandur.data.api.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/api/plant")
    suspend fun getAllPlants(): PlantResponse

    @GET("/api/plant/{id}")
    suspend fun getPlantById(@Path("id") id: Int): PlantItemResponse
}