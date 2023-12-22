package com.hackerton.noahah.presentation.ui.service.hear

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hackerton.noahah.R
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.FragmentHearBinding
import com.hackerton.noahah.presentation.base.BaseFragment
import com.hackerton.noahah.presentation.ui.service.ServiceViewModel
import com.hackerton.noahah.presentation.util.TextToSpeechManager
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class HearFragment : BaseFragment<FragmentHearBinding>(R.layout.fragment_hear) {

    private val parentViewModel: ServiceViewModel by activityViewModels()
    private val viewModel: HearViewModel by viewModels()
    private val args: HearFragmentArgs by navArgs()
    private val type by lazy { args.type }

    private lateinit var textToSpeechManager: TextToSpeechManager

    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setPdfId(parentViewModel.getPdfId())
        if(viewModel.uiState.value.modeNum == 1) {
            textToSpeechManager = TextToSpeechManager(requireContext(), SpeechMessage.COMPLETE_BRAILLE.message, ::startBraille)
        } else {
            textToSpeechManager = TextToSpeechManager(requireContext(), SpeechMessage.COMPLETE_VOICE.message, ::startVoice)

        }


        viewModel.setPdfId(parentViewModel.getPdfId())
        viewModel.setDataType(type)
    }

    private fun startBraille() {
        // TODO 점자버전 다운로드 진행관련 수행

    }

    private fun startVoice() {
        // TODO 받아온
    }
}