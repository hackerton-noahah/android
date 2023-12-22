package com.hackerton.noahah.presentation.ui.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor() : ViewModel() {

    private var pdfId: Int = -1

    val type = MutableSharedFlow<String>()

    fun setPdfId(id: Int){
        pdfId = id
    }

    fun getPdfId() = pdfId

    fun setType(data : String){
        viewModelScope.launch {
            type.emit(data)
        }
    }

}