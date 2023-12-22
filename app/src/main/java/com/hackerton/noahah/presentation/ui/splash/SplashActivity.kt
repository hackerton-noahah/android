package com.hackerton.noahah.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivitySplashBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.main.MainActivity
import com.hackerton.noahah.presentation.util.TextToSpeechManager

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeechManager = TextToSpeechManager(this, SpeechMessage.SPLASH_MENT.message, ::moveToMainActivity)
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        textToSpeechManager.destroy()
        super.onDestroy()
    }
}