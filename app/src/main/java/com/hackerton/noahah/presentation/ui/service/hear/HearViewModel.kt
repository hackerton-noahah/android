package com.hackerton.noahah.presentation.ui.service.hear

import androidx.lifecycle.ViewModel
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MultipartBody
import javax.inject.Inject


data class HearUiState(
    val showText: String = "",
    val page: Int = 0,
    val hasNext: Boolean = true,
)

@HiltViewModel
class HearViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(HearUiState())
    val uiState: StateFlow<HearUiState> = _uiState.asStateFlow()

    private var pdfMultiPart: MultipartBody.Part? = null
    private var type = ""

    fun setPdfMultiPart(part: MultipartBody.Part?) {
        pdfMultiPart = part
    }

    fun setDataType(data: String) {
        type = data
        when (data) {
            BRAILLE -> getBraille()
            HEAR -> getText()
        }
    }

    private fun getText() {
        // todo text 불러오기 api 통신

    }

    private fun getBraille() {
        // todo braille 불러오기 api 통신

    }

}