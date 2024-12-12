package com.dicoding.cektandur.data.repository

import com.dicoding.cektandur.data.api.ApiService
import com.dicoding.cektandur.data.api.response.GetHistoryResponse
import com.dicoding.cektandur.data.api.response.HistoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HistoryRepository(private val apiService: ApiService) {
    suspend fun addHistory(
        userId: RequestBody,
        className: RequestBody,
        diseaseName: RequestBody,
        confidence: RequestBody,
        plantImage: MultipartBody.Part
    ): HistoryResponse {
        return apiService.addHistory(userId, className, diseaseName, confidence, plantImage)
    }

    suspend fun getHistory(userId: String): GetHistoryResponse {
        return apiService.getHistory(userId)
    }
}