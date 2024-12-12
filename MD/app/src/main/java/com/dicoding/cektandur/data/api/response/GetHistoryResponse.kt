package com.dicoding.cektandur.data.api.response

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataItem(

	@field:SerializedName("confidence")
	val confidence: String? = null,

	@field:SerializedName("analysisResult")
	val analysisResult: String? = null,

	@field:SerializedName("className")
	val className: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("diseaseName")
	val diseaseName: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
