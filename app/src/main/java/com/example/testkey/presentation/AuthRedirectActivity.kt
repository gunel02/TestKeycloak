package com.example.testkey.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.testkey.MainActivity
import com.example.testkey.data.auth.EncryptedTokenStorage
import com.example.testkey.data.auth.KeycloakAuth
import com.example.testkey.data.network.NetworkRoutes
import com.example.testkey.data.util.TemporaryStorage
import com.example.testkey.databinding.ActivityAuthRedirectBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthRedirectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthRedirectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthRedirectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent?.data
        if(uri != null && uri.toString().startsWith(NetworkRoutes.REDIRECT_URI)){
           val code = uri.getQueryParameter("code")
            val returnedState = uri.getQueryParameter("state")

            val codeVerifier = TemporaryStorage.codeVerifier
            val state = TemporaryStorage.state

            Log.d("AuthRedirect", "Received code=$code, returnedState=$returnedState, storedState=$state")


            if(code != null && returnedState == state && codeVerifier != null){
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val tokens = KeycloakAuth.exchangeCodeForTokens(
                            code,
                            codeVerifier = codeVerifier
                        )
                        EncryptedTokenStorage.saveToken(tokens)
                        TemporaryStorage.codeVerifier = null
                        TemporaryStorage.state = null

                        withContext(Dispatchers.Main){

                            startActivity(Intent(this@AuthRedirectActivity, MainActivity::class.java))
                            finish()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@AuthRedirectActivity,"Login failed", Toast.LENGTH_SHORT).show()
                            Log.e("AuthRedirect", "Login failed: ${e.message}")
                            finish()
                        }
                    }
                }
            }else{
                Log.e("AuthRedirect", "State mismatch or missing code/codeVerifier")
                finish()
            }
        }else{
            Log.e("AuthRedirect", "Invalid redirect URI")
            finish()
        }
    }
}