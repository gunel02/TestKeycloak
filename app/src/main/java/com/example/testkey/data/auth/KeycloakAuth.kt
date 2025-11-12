package com.example.testkey.data.auth

import com.example.testkey.data.KtorClient
import com.example.testkey.data.model.TokenResponse
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

object KeycloakAuth {
    private val client = KtorClient.client

    suspend fun exchangeCodeForTokens(code: String,codeVerifier: String): TokenResponse{
        return client.submitForm(
            url = "https://172.18.0.1:8443/realms/myrealm/protocol/openid-connect/token",
            formParameters = Parameters.build {
                append("grant_type","authorization_code")
                append("code",code)
                append("redirect_uri","myapp://callback")
                append("client_id","my-android-app")
                append("code_verifier",codeVerifier)
            }
        ).body()
    }
}