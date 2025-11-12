package com.example.testkey.data.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.os.SystemTraceRequestBuilder
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.testkey.data.model.TokenResponse

@Suppress("DEPRECATION")
object EncryptedTokenStorage: TokenRepository {

    private lateinit var prefs : SharedPreferences

    fun init(context: Context){
        prefs = EncryptedSharedPreferences.create(
            "auth_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveToken(token: TokenResponse) {
        prefs.edit()
            .putString("access_token",token.access_token)
            .putString("refresh_token",token.refresh_token)
            .putLong("expires_at", System.currentTimeMillis() + token.expires_in * 1000L)
            .apply()
    }

    override fun getAccessToken(): String? = prefs.getString("access_token",null)
    override fun getRefreshToken(): String? = prefs.getString("refresh_token",null)
    override fun clearTokens() = prefs.edit().clear().apply()

}
