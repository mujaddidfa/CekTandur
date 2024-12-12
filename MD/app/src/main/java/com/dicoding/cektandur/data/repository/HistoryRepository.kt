package com.dicoding.cektandur.data.repository

import com.dicoding.cektandur.data.api.ApiService
import com.dicoding.cektandur.data.api.request.HistoryRequest
import com.dicoding.cektandur.data.api.response.GetHistoryResponse
import com.dicoding.cektandur.data.api.response.HistoryResponse

class HistoryRepository(private val apiService: ApiService) {
    suspend fun addHistory(historyRequest: HistoryRequest): HistoryResponse {
        return apiService.addHistory(historyRequest)
    }

    suspend fun getHistory(userId: String): GetHistoryResponse {
        return apiService.getHistory(userId)
    }
}