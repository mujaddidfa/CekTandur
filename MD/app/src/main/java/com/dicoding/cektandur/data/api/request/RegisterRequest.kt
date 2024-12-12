package com.dicoding.cektandur.data.api.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)