package com.example.testkey.data.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testkey.data.util.TemporaryStorage
import com.example.testkey.data.util.PkceUtil
import com.example.testkey.data.util.buildAuthUrl
import java.util.UUID

object AuthManager {
    fun startKeycloakLogin(context: Context){
        val codeVerifier = PkceUtil.generateCodeVerifier()
        val codeChallenge = PkceUtil.codeChallengeFromVerifier(codeVerifier)
        val state = UUID.randomUUID().toString()

        TemporaryStorage.codeVerifier = codeVerifier
        TemporaryStorage.state = state

        val authUrl = buildAuthUrl(
            issuer = "http://10.0.2.2:8080/realms/myrealm",
            clientId = "my-android-app",
            redirectUri = "myapp://callback",
            codeChallenge = codeChallenge,
            state = state
        )
        Log.d("AuthManager", "Starting Keycloak login")
        Log.d("AuthManager", "Code Verifier: $codeVerifier")
        Log.d("AuthManager", "Code Challenge: $codeChallenge")
        Log.d("AuthManager", "State: $state")
        Log.d("AuthManager", "Auth URL: $authUrl")
        val browserIntent = Intent(Intent.ACTION_VIEW, authUrl)
        context.startActivity(browserIntent)
    }
}

// todo - in screen must show token when go back to app