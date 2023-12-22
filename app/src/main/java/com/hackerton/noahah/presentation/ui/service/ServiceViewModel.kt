package com.hackerton.noahah.presentation.ui.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ServiceEvents{
    data object ModeButtonClicked: ServiceEvents()
}

@HiltViewModel
class ServiceViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableSharedFlow<ServiceEvents>()
    val events: SharedFlow<ServiceEvents> = _events.asSharedFlow()

    private var pdfId: Int = -1

    val type = MutableSharedFlow<String>()

    fun buttonClicked(){
        viewModelScope.launch {
            _events.emit(ServiceEvents.ModeButtonClicked)
        }
    }

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