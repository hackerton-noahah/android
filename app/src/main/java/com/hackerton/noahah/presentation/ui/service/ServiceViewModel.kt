package com.hackerton.noahah.presentation.ui.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor() : ViewModel() {

    private var pdfMultiPart: MultipartBody.Part? = null

    val type = MutableSharedFlow<String>()

    fun setPdfMultiPart(part: MultipartBody.Part) {
        pdfMultiPart = part
    }

    fun getPdfMultiPart() = pdfMultiPart

    fun setType(data : String){
        viewModelScope.launch {
            type.emit(data)
        }
    }

}