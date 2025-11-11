package com.example.testkey.data.auth

import com.example.testkey.data.model.TokenResponse

interface TokenRepository {
    fun saveToken(token: TokenResponse)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
}