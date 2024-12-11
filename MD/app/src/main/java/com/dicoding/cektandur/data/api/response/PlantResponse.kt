package com.dicoding.cektandur.data.api.response

import com.google.gson.annotations.SerializedName

data class PlantResponse(

	@field:SerializedName("plants")
	val plants: List<PlantsItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PlantsItem(

	@field:SerializedName("alternative_products_links")
	val alternativeProductsLinks: List<String?>? = null,

	@field:SerializedName("causes")
	val causes: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("className")
	val className: String? = null,

	@field:SerializedName("disease_name")
	val diseaseName: String? = null,

	@field:SerializedName("idPlant")
	val idPlant: Int? = null,

	@field:SerializedName("treatments")
	val treatments: List<String?>? = null,

	@field:SerializedName("alternative_products")
	val alternativeProducts: List<String?>? = null
)
