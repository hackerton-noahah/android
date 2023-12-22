package com.hackerton.noahah.presentation.util

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.hackerton.noahah.data.model.SpeechMessage
import java.util.Locale

class TextToSpeechManager(
    context: Context,
    private val message: String,
    private val afterSpeech: () -> Unit
): TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isTTsReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTTsReady = true
            tts.language = Locale.KOREAN

            // TTS 준비되면 음성 출력 시작
            speakOut(message)
        }
    }

    private fun speakOut(text: String) {
        if (isTTsReady) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId_1")
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                    }

                    override fun onDone(utteranceId: String?) {
                        // 음성 재생이 끝나면 화면 전환
                        afterSpeech()
                    }

                    override fun onError(utteranceId: String?) {
                    }
                })
            } else {
                @Suppress("DEPRECATION")
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                // 구버전이 경우 방식이 다름
            }
        }
    }

    fun destroy(){
        tts.stop()
        tts.shutdown()
    }
}