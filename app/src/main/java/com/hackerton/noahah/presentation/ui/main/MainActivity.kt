package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var isTTsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TTS 초기화
        tts = TextToSpeech(this, this)

        binding.btnUploadPdf.setOnClickListener {
            startActivity(Intent(this,ServiceActivity::class.java))
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
        if (isTTsReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_2")
            // 음성 인식 기능 추가
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}