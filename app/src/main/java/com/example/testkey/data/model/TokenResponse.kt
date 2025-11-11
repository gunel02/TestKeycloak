package com.example.testkey.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val access_token: String,
    val refresh_token: String? = null,
    val id_token: String? = null,
    val expires_in: Int,
    val token_type: String,
    val scope: String? = null
)

