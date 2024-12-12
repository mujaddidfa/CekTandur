package com.dicoding.cektandur.data.api

import com.dicoding.cektandur.data.api.request.LoginRequest
import com.dicoding.cektandur.data.api.request.RegisterRequest
import com.dicoding.cektandur.data.api.response.GetHistoryResponse
import com.dicoding.cektandur.data.api.response.HistoryResponse
import com.dicoding.cektandur.data.api.response.LoginResponse
import com.dicoding.cektandur.data.api.response.PlantItemResponse
import com.dicoding.cektandur.data.api.response.PlantResponse
import com.dicoding.cektandur.data.api.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("/api/users/history")
    suspend fun addHistory(
        @Part("userId") userId: RequestBody,
        @Part("className") className: RequestBody,
        @Part("diseaseName") diseaseName: RequestBody,
        @Part("confidence") confidence: RequestBody,
        @Part plantImage: MultipartBody.Part
    ): HistoryResponse

    @GET("/api/users/history/{userId}")
    suspend fun getHistory(@Path("userId") userId: String): GetHistoryResponse
}