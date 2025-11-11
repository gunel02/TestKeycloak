package com.example.testkey.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testkey.R
import com.example.testkey.databinding.ActivityAuthRedirectBinding

class AuthRedirectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthRedirectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthRedirectBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}