package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.speech.tts.TextToSpeech
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity
import java.util.Locale


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) , TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var isTTsReady = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TTS 초기화
        tts = TextToSpeech(this, this)

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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTTsReady = true
            tts.language = Locale.KOREAN

            // TTS 준비되면 음성 출력 시작
            speakOut(SpeechMessage.MAIN_INIT_MENT.message)
        }
    }

    private fun speakOut(text: String) {
        if (isTTsReady)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_2")
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}