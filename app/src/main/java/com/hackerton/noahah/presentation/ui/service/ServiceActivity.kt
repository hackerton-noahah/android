package com.hackerton.noahah.presentation.ui.service

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.hackerton.noahah.databinding.ActivityServiceBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.toMultiPart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceActivity: BaseActivity<ActivityServiceBinding>(ActivityServiceBinding::inflate) {

    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("pdfUri")){
            intent.getStringExtra("pdfUri")?.let{ pdfUri ->
                val pdfMultiPart = pdfUri.toUri().toMultiPart(this)
                viewModel.setPdfMultiPart(pdfMultiPart)
            }
        }
    }

}