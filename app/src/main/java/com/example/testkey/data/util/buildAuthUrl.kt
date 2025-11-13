package com.example.testkey.data.util

import android.net.Uri
import android.util.Log


fun buildAuthUrl(
    issuer:String,
    clientId: String,
    redirectUri: String,
    codeChallenge: String,
    state: String
): Uri {

    val authEndpoint = "${issuer.trimEnd('/')}/protocol/openid-connect/auth"
    Log.d("AuthDebug", "Auth Endpoint: $authEndpoint")

    return Uri.parse(authEndpoint).buildUpon()
        .appendQueryParameter("response_type","code")
        .appendQueryParameter("client_id",clientId)
        .appendQueryParameter("redirect_uri",redirectUri)
        .appendQueryParameter("scope", listOf("openid", "profile", "email").joinToString(" "))
        .appendQueryParameter("state",state)
        .appendQueryParameter("code_challenge",codeChallenge)
        .appendQueryParameter("code_challenge_method","S256")
        .build()
}

