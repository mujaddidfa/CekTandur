package com.dicoding.cektandur.data.api.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class HistoryData(

	@field:SerializedName("confidence")
	val confidence: Int? = null,

	@field:SerializedName("analysisResult")
	val analysisResult: String? = null,

	@field:SerializedName("className")
	val className: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("plantName")
	val plantName: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
