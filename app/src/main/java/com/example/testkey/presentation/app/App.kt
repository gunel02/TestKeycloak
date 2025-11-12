package com.example.testkey.presentation.app

import android.app.Application
import com.example.testkey.data.auth.EncryptedTokenStorage

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        EncryptedTokenStorage.init(this)
    }
}