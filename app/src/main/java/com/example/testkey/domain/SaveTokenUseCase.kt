package com.example.testkey.domain

import com.example.testkey.data.auth.TokenRepository
import com.example.testkey.data.model.TokenResponse

class SaveTokenUseCase(private val tokenRepository: TokenRepository) {
    fun execute (token: TokenResponse) = tokenRepository.saveToken(token)
}