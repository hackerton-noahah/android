package com.hackerton.noahah.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hackerton.noahah.databinding.ActivitySplashBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}