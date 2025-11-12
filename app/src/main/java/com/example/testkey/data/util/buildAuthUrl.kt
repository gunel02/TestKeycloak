package com.example.testkey.data.util

import android.net.Uri


fun buildAuthUrl(
    issuer:String,
    clientId: String,
    redirectUri: String,
    codeChallenge: String,
    state: String
): Uri {

    val authEndpoint = "$issuer/protocol/openid-connect/auth"
    return Uri.parse(authEndpoint).buildUpon()
        .appendQueryParameter("response_type","code")
        .appendQueryParameter("client_id",clientId)
        .appendQueryParameter("redirect_uri",redirectUri)
        .appendQueryParameter("scope","openid profile email")
        .appendQueryParameter("state",state)
        .appendQueryParameter("code_challenge",codeChallenge)
        .appendQueryParameter("code_challenge_method","S256")
        .build()
}

