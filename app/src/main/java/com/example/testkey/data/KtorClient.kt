package com.example.testkey.data

import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import com.example.testkey.data.auth.EncryptedTokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorClient {

    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorLogging", message)
                }
            }
        }

        defaultRequest {
            val token = EncryptedTokenStorage.getAccessToken()
            if(!token.isNullOrEmpty()){
                header("Authorization","Bearer $token")
            }
        }
    }

}