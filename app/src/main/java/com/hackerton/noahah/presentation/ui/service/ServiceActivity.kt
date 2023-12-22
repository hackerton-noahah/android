package com.hackerton.noahah.presentation.ui.service

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.hackerton.noahah.data.model.SpeechErrorMessage
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.ActivityServiceBinding
import com.hackerton.noahah.presentation.base.BaseActivity
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import com.hackerton.noahah.presentation.util.Constants.TAG
import com.hackerton.noahah.presentation.util.TextToSpeechManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceActivity : BaseActivity<ActivityServiceBinding>(ActivityServiceBinding::inflate) {

    private val viewModel: ServiceViewModel by viewModels()
    private lateinit var textToSpeechManager: TextToSpeechManager

    val PERMISSION = 1

    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEventObserve()
        textToSpeechManager =
            TextToSpeechManager(this, SpeechMessage.MODE_INIT_MENT.message, ::startObserverVoice)

        recordSetting()

        if (intent.hasExtra("pdfId")) {
            intent.getIntExtra("pdfId", -1).let { pdfId ->
                viewModel.setPdfId(pdfId)
            }
        }


    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is ServiceEvents.ModeButtonClicked -> {
                        Handler(Looper.getMainLooper()).post {
                            textToSpeechManager.destroy()
                            speechRecognizer.destroy()
                        }
                    }
                }
            }
        }
    }

    private fun recordSetting() {
        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO
            ), PERMISSION
        )

        // RecognizerIntent 생성
        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 언어 설정
    }

    private fun startObserverVoice() {
        Handler(Looper.getMainLooper()).post {
            // 음성 재생이 끝나면 음성 인식 시작
            speechRecognizer =
                SpeechRecognizer.createSpeechRecognizer(this@ServiceActivity); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
            speechRecognizer.setRecognitionListener(listener); // 리스너 설정
            speechRecognizer.startListening(intent); // 듣기 시작
        }
    }

    private fun restartObserverVoice() {
        Handler(Looper.getMainLooper()).post {
            speechRecognizer.destroy()
            // 음성 재생이 끝나면 음성 인식 시작
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@ServiceActivity); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
            speechRecognizer.setRecognitionListener(listener); // 리스너 설정
            speechRecognizer.startListening(intent); // 듣기 시작
        }
    }

    private val listener: RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
        }

        override fun onBeginningOfSpeech() {

        }

        override fun onRmsChanged(rmsdB: Float) {
        }

        override fun onBufferReceived(buffer: ByteArray) {

        }

        override fun onEndOfSpeech() {
        }

        override fun onError(error: Int) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            val message: String
            message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러입니다."
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 문제입니다."
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "권한이 없습니다"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러입니다."
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트워크 시간 초과입니다."
                SpeechRecognizer.ERROR_NO_MATCH -> "음성이 인식되지 않았습니다. 다시 말씀해주세요"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "잠시 후에 다시 시도해주세요."
                SpeechRecognizer.ERROR_SERVER -> "서버 문제입니다."
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말씀하신 문장이 너무 깁니다.. 다시 말씀해주세요"
                else -> "알 수 없는 오류입니다."
            }
            when(message) {
                "음성이 인식되지 않았습니다. 다시 말씀해주세요",
                "잠시 후에 다시 시도해주세요.",
                "말씀하신 문장이 너무 깁니다.. 다시 말씀해주세요" -> {
                    textToSpeechManager = TextToSpeechManager(this@ServiceActivity, message, ::restartObserverVoice)
                }
            }
        }

        override fun onResults(results: Bundle) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            var userInput: String = ""
            for (i in matches!!.indices) {
                userInput += matches[i]
            }

            Log.d(TAG, userInput)

            if(userInput.contains("음성")){
                Handler(Looper.getMainLooper()).post {
                    speechRecognizer.destroy()
                    textToSpeechManager.destroy()
                }
                viewModel.setType(HEAR)
            } else if(userInput.contains("점자")){
                Handler(Looper.getMainLooper()).post {
                    speechRecognizer.destroy()
                    textToSpeechManager.destroy()
                }
                viewModel.setType(BRAILLE)
            } else {
                textToSpeechManager = TextToSpeechManager(this@ServiceActivity,
                    SpeechErrorMessage.NOT_EXIST_MODE.message, ::restartObserverVoice)
            }
        }

        override fun onPartialResults(partialResults: Bundle) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        override fun onEvent(eventType: Int, params: Bundle) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    }

    override fun onRestart() {
        textToSpeechManager =
            TextToSpeechManager(this, SpeechMessage.MODE_INIT_MENT.message, ::startObserverVoice)
        super.onRestart()
    }

    override fun onDestroy() {
        Handler(Looper.getMainLooper()).post {
            speechRecognizer.destroy()
            textToSpeechManager.destroy()
        }
        super.onDestroy()
    }
}