package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity
import com.hackerton.noahah.presentation.util.TextToSpeechManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var textToSpeechManager: TextToSpeechManager
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEventObserve()
        textToSpeechManager = TextToSpeechManager(this, SpeechMessage.MAIN_INIT_MENT.message) {}

        binding.btnUploadPdf.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                textToSpeechManager.destroy()
            }

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            requestPDF.launch(intent)
        }
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MainEvents.ShowToastMessage -> showCustomToast(it.msg)
                    is MainEvents.GoToServiceActivity -> {
                        val intent = Intent(this@MainActivity, ServiceActivity::class.java)
                            .putExtra("pdfId", it.pdfId)
                        startActivity(intent)
                    }

                    is MainEvents.ShowLoading -> {
                        showLoading(this@MainActivity)
                    }

                    is MainEvents.DismissLoading -> dismissLoading()
                }
            }
        }
    }

    private val requestPDF =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri ->
                CoroutineScope(Dispatchers.Main).launch {
                    showLoading(this@MainActivity)
                    delay(3000)
                    val intent = Intent(this@MainActivity, ServiceActivity::class.java)
                        .putExtra("pdfId", 0)
                    startActivity(intent)
                }
            }
        }

    override fun onDestroy() {
        Handler(Looper.getMainLooper()).post {
            textToSpeechManager.destroy()
        }
        super.onDestroy()
    }
}