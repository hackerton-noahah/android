package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnUploadPdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            requestPDF.launch(intent)
        }
    }

    private val requestPDF =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri->
                val intent = Intent(this, ServiceActivity::class.java)
                    .putExtra("pdfUri", uri.toString())
                startActivity(intent)
            }
        }

}