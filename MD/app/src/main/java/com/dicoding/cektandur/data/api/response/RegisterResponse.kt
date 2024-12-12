package com.dicoding.cektandur.data.api.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)