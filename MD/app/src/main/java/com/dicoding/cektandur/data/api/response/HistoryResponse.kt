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

	@field:SerializedName("className")
	val className: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("diseaseName")
	val diseaseName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("causes")
	val causes: List<String?>? = null,

	@field:SerializedName("treatments")
	val treatments: List<String?>? = null,

	@field:SerializedName("alternative_products")
	val alternativeProducts: List<String?>? = null,

	@field:SerializedName("alternative_products_links")
	val alternativeProductsLinks: List<String?>? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null
)
