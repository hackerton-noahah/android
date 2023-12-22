package com.hackerton.noahah.presentation.ui.service.hear

import androidx.lifecycle.ViewModel
import com.hackerton.noahah.data.repository.HearDfRepository
import com.hackerton.noahah.presentation.util.Constants.BRAILLE
import com.hackerton.noahah.presentation.util.Constants.HEAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class HearUiState(
    val mode: String = "",
    val showText: String = "",
    val page: Int = 0,
    val hasNext: Boolean = true,
)

@HiltViewModel
class HearViewModel @Inject constructor(
    private val hearDfRepository: HearDfRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HearUiState())
    val uiState: StateFlow<HearUiState> = _uiState.asStateFlow()

    private var pdfId = -1
    private var type = ""

    fun setPdfId(id: Int) {
        pdfId = id
    }

    fun setDataType(data: String) {
        type = data
        when (data) {
            BRAILLE -> {
                _uiState.update { state ->
                    state.copy(
                        mode = " : 점자모드"
                    )
                }
                getBraille()
            }

            HEAR -> {
                _uiState.update { state ->
                    state.copy(
                        mode = " : 음성모드"
                    )
                }
                getText()
            }
        }
    }

    private fun getText() {
        // todo text 값 불러오기
    }

    private fun getBraille() {
        // todo text 값 불러오기
    }


}