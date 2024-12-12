package com.dicoding.cektandur.data.api.request

data class HistoryRequest(
    val userId: String,
    val className: String,
    val diseaseName: String,
    val analysisResult: String,
    val confidence: Float
)