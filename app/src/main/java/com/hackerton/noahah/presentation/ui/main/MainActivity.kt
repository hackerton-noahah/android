package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.hackerton.noahah.data.model.SpeechErrorMessage
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity
import com.hackerton.noahah.presentation.util.Constants.TAG
import java.util.Locale


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) , TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var isTTsReady = false

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TTS 초기화
        tts = TextToSpeech(this, this)

        // STT 초기화
        initializeSpeechRecognizer()

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
        if (isTTsReady) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_2")
            // 음성인식 시작
            startListening()
        }
    }

    private fun initializeSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    processResults(matches[0])
                }
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                restartListeningService() // 에러 발생 시 다시 시작
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun startListening() {
        speechRecognizer.startListening(recognizerIntent)
    }

    private fun processResults(command: String) {
        // 임의의 문자열 포함 여부 확인
        Log.d(TAG,command)
        if (command.contains("노아", ignoreCase = true)) {
            goToNextActivity()
        } else {
            // 메시지 출력 후 다시 듣기
            showMessage(SpeechErrorMessage.NOT_EXIST_FILE_NAME.message)
            restartListeningService()
        }
    }

    private fun goToNextActivity() {
        val intent = Intent(this, ServiceActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMessage(message: String) {
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_4")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun restartListeningService() {
        speechRecognizer.stopListening()
        speechRecognizer.startListening(recognizerIntent)
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        speechRecognizer.destroy()
        super.onDestroy()
    }
}