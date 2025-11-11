package com.example.testkey.data.auth

import android.content.Context
import android.content.Intent
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
            redirectUri = "com.example.testkey:/oauth2redirect",
            codeChallenge = codeChallenge,
            state = state
        )

        val browserIntent = Intent(Intent.ACTION_VIEW, authUrl)
        context.startActivity(browserIntent)
    }
}