package com.hackerton.noahah.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackerton.noahah.data.model.BaseState
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import com.hackerton.noahah.data.model.request.RegisterPdfRequest
import com.hackerton.noahah.data.repository.HearDfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


sealed class MainEvents {
    data class GoToServiceActivity(val pdfId: Int) : MainEvents()
    data class ShowToastMessage(val msg: String) : MainEvents()
    data object ShowLoading : MainEvents()
    data object DismissLoading : MainEvents()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hearDfRepository: HearDfRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<MainEvents>()
    val events: SharedFlow<MainEvents> = _events.asSharedFlow()

    fun setFile(file: MultipartBody.Part) {
        getFileUrl(file)
    }

    private fun getFileUrl(file: MultipartBody.Part) {
        viewModelScope.launch {
            hearDfRepository.getFileUrl(
                GetFileUrlRequest(
                    file
                )
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        registerPdf(it.body.imgUrl)
                    }

                    is BaseState.Error -> {
                        _events.emit(MainEvents.ShowToastMessage(it.msg))
                    }
                }
            }
        }
    }

    private fun registerPdf(url: String) {
        viewModelScope.launch {
            hearDfRepository.registerPdf(
                RegisterPdfRequest("temp", url)
            ).let {
                when (it) {
                    is BaseState.Success -> {
                        _events.emit(MainEvents.GoToServiceActivity(it.body.id))
                    }

                    is BaseState.Error -> {
                        _events.emit(MainEvents.ShowToastMessage(it.msg))
                    }
                }
            }
        }
    }

}