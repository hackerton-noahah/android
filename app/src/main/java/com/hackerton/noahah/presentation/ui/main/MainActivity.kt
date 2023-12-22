package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnUploadPdf.setOnClickListener {
            startActivity(Intent(this,ServiceActivity::class.java))
        }
    }
}