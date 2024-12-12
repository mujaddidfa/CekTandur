package com.dicoding.cektandur.data.api.response

import com.google.gson.annotations.SerializedName

data class PlantItemResponse(

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("plant")
    val plant: Plant
)

data class Plant(

    @SerializedName("idPlant")
    val idPlant: Int,

    @SerializedName("className")
    val className: String,

    @SerializedName("disease_name")
    val diseaseName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("causes")
    val causes: List<String>,

    @SerializedName("treatments")
    val treatments: List<String>,

    @SerializedName("alternative_products")
    val alternativeProducts: List<String>,

    @SerializedName("alternative_products_links")
    val alternativeProductsLinks: List<String>
)