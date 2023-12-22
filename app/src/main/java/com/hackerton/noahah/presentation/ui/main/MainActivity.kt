package com.hackerton.noahah.presentation.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hackerton.noahah.Manifest
import com.hackerton.noahah.data.model.SpeechErrorMessage
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityMainBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.ui.service.ServiceActivity
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
//        initializeSpeechRecognizer()

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
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                }

                override fun onDone(utteranceId: String?) {
                    // 음성인식 시작
//                    startListening()
                }

                override fun onError(utteranceId: String?) {
                }
            })
        }
    }

//
//    private fun initializeSpeechRecognizer() {
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
//            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//        }
//
//        speechRecognizer.setRecognitionListener(object : RecognitionListener {
//            override fun onResults(results: Bundle?) {
//                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//                if (matches != null) {
//                    // 결과 출력
//                    val recognizedText = matches[0]
//                    Log.d("STT Result", recognizedText) // 로그로 출력
//                    Toast.makeText(this@MainActivity, recognizedText, Toast.LENGTH_LONG).show() // 토스트 메시지로 출력
//                    processResults(recognizedText)
//                }
//            }
//
//            override fun onReadyForSpeech(params: Bundle?) {}
//            override fun onBeginningOfSpeech() {}
//            override fun onRmsChanged(rmsdB: Float) {}
//            override fun onBufferReceived(buffer: ByteArray?) {}
//            override fun onEndOfSpeech() {}
//            override fun onError(error: Int) {
//                Log.d("STT Error", "Error code: $error") // 에러 로그 출력
//                restartListeningService() // 에러 발생 시 다시 시작
//            }
//
//            override fun onPartialResults(partialResults: Bundle?) {}
//            override fun onEvent(eventType: Int, params: Bundle?) {}
//        })
//    }
//
//    private fun startListening() {
//        speechRecognizer.startListening(recognizerIntent)
//    }
//
//    private fun processResults(command: String) {
//        // 임의의 문자열 포함 여부 확인
//        if (command.contains("노아", ignoreCase = true)) {
//            goToNextActivity()
//        } else {
//            // 메시지 출력 후 다시 듣기
//            showMessage(SpeechErrorMessage.NOT_EXIST_FILE_NAME.message)
//            restartListeningService()
//        }
//    }
//
//    private fun goToNextActivity() {
//        val intent = Intent(this, ServiceActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    private fun showMessage(message: String) {
//        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_4")
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun restartListeningService() {
//        speechRecognizer.stopListening()
//        speechRecognizer.startListening(recognizerIntent)
//    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        speechRecognizer.destroy()
        super.onDestroy()
    }
}