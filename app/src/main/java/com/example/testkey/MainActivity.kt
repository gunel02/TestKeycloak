package com.example.testkey

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.testkey.data.auth.AuthManager
import com.example.testkey.data.auth.EncryptedTokenStorage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val token = EncryptedTokenStorage.getAccessToken()
        if(token.isNullOrEmpty()){
            AuthManager.startKeycloakLogin(this)
        }
        else{
            Toast.makeText(this,"Already logged in", Toast.LENGTH_SHORT).show()
        }
    }
}