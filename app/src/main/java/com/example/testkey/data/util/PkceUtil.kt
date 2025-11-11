package com.example.testkey.data.util


import java.security.SecureRandom
import android.util.Base64
import java.security.MessageDigest

object PkceUtil {

    fun generateCodeVerifier(): String{
        val random = ByteArray(32)
        SecureRandom().nextBytes(random)
        return Base64.encodeToString(random,Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING).take(128)
    }

    fun codeChallengeFromVerifier(verifier: String): String{
        val bytes = verifier.toByteArray(Charsets.US_ASCII)
        val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
        return Base64.encodeToString(digest, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }
}