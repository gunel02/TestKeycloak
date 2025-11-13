package com.example.testkey

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.testkey.data.auth.AuthManager
import com.example.testkey.data.auth.EncryptedTokenStorage
import com.example.testkey.data.model.TokenResponse
import com.example.testkey.data.network.NetworkRoutes
import com.example.testkey.databinding.ActivityAuthRedirectBinding
import com.example.testkey.databinding.ActivityMainBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = EncryptedTokenStorage.getAccessToken()
//        Log.e("AuthRedirect", "Invalid redirect URI")
        if(token.isNullOrEmpty()){
            AuthManager.startKeycloakLogin(this)
        }
        else{
            Toast.makeText(this,"Already logged in", Toast.LENGTH_SHORT).show()
        }
    }
}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val data = intent.data
//
//        Log.d("MainActivity", "onCreate - intent action: ${intent.action}")
//        Log.d("MainActivity", "onCreate - intent data: $data")
//
//        if (data != null && data.toString().startsWith(NetworkRoutes.REDIRECT_URI)) {
//
//            val code = data.getQueryParameter("code")
//            val error = data.getQueryParameter("error")
//
//            Log.d("MainActivity", "Callback received - code: $code, error: $error")
//
//            if (error != null) {
//                binding.accessToken.text = "Error: $error"
//            } else if (code != null) {
//                getTokenFromKeycloak(code)
//            }
//        } else {
//            val token = EncryptedTokenStorage.getAccessToken()
//
//            if (!token.isNullOrEmpty()) {
//                Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show()
//                binding.accessToken.text = "Stored Token: ${token.take(50)}..."
//            } else {
//                binding.accessToken.text = "Starting login..."
//                AuthManager.startKeycloakLogin(this)
//            }
//        }
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        setIntent(intent)
//
//        val data = intent.data
//        Log.d("MainActivity", "onNewIntent - data: $data")
//
//        if (data != null && data.toString().startsWith(NetworkRoutes.REDIRECT_URI)) {
//            val code = data.getQueryParameter("code")
//            Log.d("MainActivity", "Auth code from onNewIntent: $code")
//
//            if (code != null) {
//                getTokenFromKeycloak(code)
//            }
//        }
//    }
//
//    private fun getTokenFromKeycloak(code: String) {
//        val tokenUrl = NetworkRoutes.KEYCLOAK_URL
//        val client = OkHttpClient()
//
//        val formBody = FormBody.Builder()
//            .add("grant_type", "authorization_code")
//            .add("code", code)
//            .add("redirect_uri", NetworkRoutes.REDIRECT_URI)
//            .add("client_id", NetworkRoutes.CLIENT_ID)
//            .build()
//
//        val request = Request.Builder()
//            .url(tokenUrl)
//            .post(formBody)
//            .build()
//
//        runOnUiThread {
//            binding.accessToken.text = "Exchanging code for tokens..."
//        }
//
//        Thread {
//            try {
//                val response = client.newCall(request).execute()
//                val body = response.body?.string()
//
//                Log.d("MainActivity", "Response code: ${response.code}")
//                Log.d("MainActivity", "Response body: $body")
//
//                if (response.isSuccessful && body != null) {
//                    val json = JSONObject(body)
//
//                    if (json.has("error")) {
//                        val error = json.getString("error")
//                        val errorDesc = json.optString("error_description", "")
//
//                        runOnUiThread {
//                            binding.accessToken.text = "Error: $error\n$errorDesc"
//                        }
//                        return@Thread
//                    }
//
//                    val accessToken = json.optString("access_token", "")
//                    val refreshToken = json.optString("refresh_token", null)
//                    val idToken = json.optString("id_token", null)
//                    val expiresIn = json.optInt("expires_in", 0)
//                    val tokenType = json.optString("token_type", "")
//                    val scope = json.optString("scope", null)
//
//                    runOnUiThread {
//                        binding.accessToken.text = "Access Token: ${accessToken.take(50)}..."
//                        binding.refreshToken.text = "Refresh Token: ${refreshToken?.take(50) ?: "N/A"}..."
//                        binding.expiresIn.text = "Expires In: $expiresIn seconds"
//                        binding.tokenType.text = "Token Type: $tokenType"
//                    }
//
//                    if (accessToken.isNotEmpty()) {
//                        val tokenResponse = TokenResponse(
//                            access_token = accessToken,
//                            refresh_token = refreshToken,
//                            id_token = idToken,
//                            expires_in = expiresIn,
//                            token_type = tokenType,
//                            scope = scope
//                        )
//                        EncryptedTokenStorage.saveToken(tokenResponse)
//
//                        runOnUiThread {
//                            Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
//                        }
//                    }
//
//                } else {
//                    runOnUiThread {
//                        binding.accessToken.text = "Request failed: ${response.code}\n$body"
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Error", e)
//                runOnUiThread {
//                    binding.accessToken.text = "Error: ${e.message}"
//                }
//            }
//        }.start()
//    }
//}
