package com.vinaastems.sync.dto

data class LoginRequest(
    val userId: String,
    val password: String,
    val storeCode: String?,
    val posNo: String?
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val authToken: String? = null
)
