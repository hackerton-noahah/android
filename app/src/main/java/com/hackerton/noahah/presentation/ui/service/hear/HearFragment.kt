package com.hackerton.noahah.presentation.ui.service.hear

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hackerton.noahah.R
import com.hackerton.noahah.data.model.SpeechErrorMessage
import com.hackerton.noahah.data.model.SpeechMessage
import com.hackerton.noahah.databinding.FragmentHearBinding
import com.hackerton.noahah.presentation.base.BaseFragment
import com.hackerton.noahah.presentation.ui.service.ServiceViewModel
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import com.hackerton.noahah.presentation.util.TextToSpeechManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HearFragment : BaseFragment<FragmentHearBinding>(R.layout.fragment_hear) {

    private val parentViewModel: ServiceViewModel by activityViewModels()
    private val viewModel: HearViewModel by viewModels()
    private val args: HearFragmentArgs by navArgs()
    private val type by lazy { args.type }

    private lateinit var textToSpeechManager: TextToSpeechManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setPdfId(parentViewModel.getPdfId())
        viewModel.setDataType(type)

        when (type) {
            BRAILLE -> {
                binding.tvResult.text = "⠛⠗⠡ ⠏⠈⠥: \"Java ⠒⠈⠗⠉\"\n" +
                        "⠛⠢⠃: ⠢ ⠛⠗⠛⠑⠃⠑⠛⠡⠅ ⠥⠂⠡ Java ⠛⠗⠉⠛⠡⠅ ⠉⠛⠒⠥⠡ ⠛⠥⠂⠡⠅ ⠥⠂⠡ ⠛⠅⠛⠅⠑⠃⠑⠛⠡⠅. ⠂⠂⠢⠃, ⠒⠂⠥⠂⠡ ⠥⠂⠡⠛⠅, ⠉⠛⠒⠂⠡, ⠡⠛⠡⠒⠡⠅⠉⠅, ⠛⠂⠛⠅⠉⠅ ⠒⠡⠉ ⠂⠡⠉⠡⠅⠅⠛⠅ ⠛⠗⠉⠛⠡⠅⠅⠅⠛⠅ ⠒⠂ ⠒⠂⠡⠅⠅⠉.\n" +
                        "\n" +
                        "⠛⠗⠡ ⠏⠈⠥: \"⠡⠂⠗⠉⠛⠅⠡⠅⠥ ⠒⠂⠂\"\n" +
                        "⠛⠢⠃: ⠂⠗⠂⠡, ⠛⠒⠥⠥, ⠥⠥⠉, ⠏⠥⠒, ⠥⠗⠒, ⠛⠗⠂⠛⠉ ⠒⠡⠉ ⠒⠂⠅⠂⠥⠅⠅⠅ ⠡⠂⠗⠉⠛⠅⠡⠅⠥ ⠒⠂⠂ ⠛⠂⠛⠅⠉, ⠛⠅ ⠡⠂⠗⠉⠛⠅⠡⠅⠥⠅ ⠥⠒⠡⠅⠛⠅ ⠛⠅ ⠥⠂⠉⠉ ⠥⠡⠅, ⠡⠂⠅⠒⠂⠉⠅⠅ ⠛⠒ ⠛⠡⠛⠅⠂⠅⠅."
                textToSpeechManager = TextToSpeechManager(
                    requireContext(),
                    SpeechMessage.COMPLETE_BRAILLE.message,
                    ::startBraille
                )
            }

            HEAR -> {
                binding.tvResult.text = "강의명: \"Java 기초\"\n" +
                        "내용: 이 강의에서는 Java 프로그래밍 언어의 기초를 배우게 됩니다. 변수, 데이터 타입, 연산자, 조건문, 반복문 등 기본적인 프로그래밍 \n" +
                        "개념을 다룹니다. 강의명: \"자료구조 이해\""
                textToSpeechManager = TextToSpeechManager(
                    requireContext(),
                    "1페이지입니다. " + SpeechMessage.COMPLETE_VOICE.message,
                    ::startVoice
                )
            }
        }
//
//        viewModel.setPdfId(parentViewModel.getPdfId())

    }

    private fun startBraille() {
        // TODO 점자버전 다운로드 진행관련 수행
        val textList = arrayListOf<String>().apply {
            add("⠛⠗⠡ ⠏⠈⠥: \"Java ⠒⠈⠗⠉\"\n" +
                    "⠛⠢⠃: ⠢ ⠛⠗⠛⠑⠃⠑⠛⠡⠅ ⠥⠂⠡ Java ⠛⠗⠉⠛⠡⠅ ⠉⠛⠒⠥⠡ ⠛⠥⠂⠡⠅ ⠥⠂⠡ ⠛⠅⠛⠅⠑⠃⠑⠛⠡⠅. ⠂⠂⠢⠃, ⠒⠂⠥⠂⠡ ⠥⠂⠡⠛⠅, ⠉⠛⠒⠂⠡, ⠡⠛⠡⠒⠡⠅⠉⠅, ⠛⠂⠛⠅⠉⠅ ⠒⠡⠉ ⠂⠡⠉⠡⠅⠅⠛⠅ ⠛⠗⠉⠛⠡⠅⠅⠅⠛⠅ ⠒⠂ ⠒⠂⠡⠅⠅⠉.\n" +
                    "\n" +
                    "⠛⠗⠡ ⠏⠈⠥: \"⠡⠂⠗⠉⠛⠅⠡⠅⠥ ⠒⠂⠂\"\n" +
                    "⠛⠢⠃: ⠂⠗⠂⠡, ⠛⠒⠥⠥, ⠥⠥⠉, ⠏⠥⠒, ⠥⠗⠒, ⠛⠗⠂⠛⠉ ⠒⠡⠉ ⠒⠂⠅⠂⠥⠅⠅⠅ ⠡⠂⠗⠉⠛⠅⠡⠅⠥ ⠒⠂⠂ ⠛⠂⠛⠅⠉, ⠛⠅ ⠡⠂⠗⠉⠛⠅⠡⠅⠥⠅ ⠥⠒⠡⠅⠛⠅ ⠛⠅ ⠥⠂⠉⠉ ⠥⠡⠅, ⠡⠂⠅⠒⠂⠉⠅⠅ ⠛⠒ ⠛⠡⠛⠅⠂⠅⠅.")
            add("⠛⠗⠡ ⠏⠈⠥: \"⠛⠉⠥⠒⠡⠡⠅⠂⠛⠅ ⠛⠗⠉⠛⠡⠅⠉\"\n" +
                    "⠛⠢⠃: ⠛⠉⠥⠒⠡⠡⠅⠂⠛⠅ ⠛⠗⠉⠛⠡⠅⠉⠅ ⠛⠗⠛⠛⠡⠅ ⠛⠅ ⠡⠂⠅⠡⠅⠉ ⠛⠂⠛⠅⠉, ⠥⠒⠛⠡ ⠏⠉⠒⠅ ⠥⠛ ⠡⠏⠅⠒⠛⠅⠉⠉ ⠛⠉⠥⠒⠡⠡⠅⠂⠛⠅⠡⠅ ⠥⠂⠉⠉ ⠛⠂⠃⠥⠅⠅ ⠥⠛⠅⠒.")
        }


        var idx: Int = 0
        binding.apply {
            btnPrevious.setOnClickListener {
                when(idx) {
                    0 -> {
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(),
                            SpeechErrorMessage.IS_FIRST_PAGE.message,
                            ::emptyFun
                        )
                    }
                    else -> {
                        idx--
                        tvResult.text = textList.get(idx)
                    }
                }
            }
            btnNext.setOnClickListener {
                when(idx) {
                    textList.size - 1 -> {
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(),
                            SpeechErrorMessage.IS_LAST_PAGE.message,
                            ::emptyFun
                        )
                    }
                    else -> {
                        idx++
                        tvResult.text = textList.get(idx)
                    }
                }
            }
        }
    }

    private fun startVoice() {
        val textList = arrayListOf<String>().apply {
            add("강의명: \"Java 기초\"\n" +
                    "내용: 이 강의에서는 Java 프로그래밍 언어의 기초를 배우게 됩니다. 변수, 데이터 타입, 연산자, 조건문, 반복문 등 기본적인 프로그래밍 \n" +
                    "개념을 다룹니다. 강의명: \"자료구조 이해\"")
            add("강의명: \"객체지향 프로그래밍\"\n" +
                    "내용: 객체지향 프로그래밍의 원리와 장점을 배우고, 실제 코드를 \n" +
                    "작성하며 객체지향적 사고 방식을 훈련합니다. 강의명: \"데이터베이스 기초\"")
        }

        var idx: Int = 0
        textToSpeechManager = TextToSpeechManager(
            requireContext(),
            binding.tvResult.text.toString(),
            ::emptyFun
        )
        binding.apply {
            btnPrevious.setOnClickListener {
                when (idx) {
                    0 -> {
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(),
                            SpeechErrorMessage.IS_FIRST_PAGE.message,
                            ::emptyFun
                        )
                    }

                    else -> {
                        idx--
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(), "${idx + 1}페이지 입니다. " +
                                    textList.get(idx),
                            ::emptyFun
                        )
                        tvResult.text = textList.get(idx)

                    }
                }
            }
            btnNext.setOnClickListener {
                when (idx) {
                    textList.size - 1 -> {
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(),
                            SpeechErrorMessage.IS_LAST_PAGE.message,
                            ::emptyFun
                        )
                    }

                    else -> {
                        idx++
                        textToSpeechManager = TextToSpeechManager(
                            requireContext(), "${idx + 1}페이지 입니다. " +
                                    textList.get(idx),
                            ::emptyFun
                        )
                        tvResult.text = textList.get(idx)
                    }
                }
            }
        }
    }

    private fun emptyFun() {

    }
}