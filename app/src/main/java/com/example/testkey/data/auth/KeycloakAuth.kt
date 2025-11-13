package com.example.testkey.data.auth

import com.example.testkey.data.KtorClient
import com.example.testkey.data.model.TokenResponse
import com.example.testkey.data.network.NetworkRoutes
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

object KeycloakAuth {
    private val client = KtorClient.client

    suspend fun exchangeCodeForTokens(code: String,codeVerifier: String): TokenResponse {
        return client.submitForm(
            url = NetworkRoutes.KEYCLOAK_URL,
            formParameters = Parameters.build {
                append("grant_type","authorization_code")
                append("code",code)
                append("redirect_uri", NetworkRoutes.REDIRECT_URI)
                append("client_id",NetworkRoutes.CLIENT_ID)
                append("code_verifier",codeVerifier)
            }
        ).body()
    }
}