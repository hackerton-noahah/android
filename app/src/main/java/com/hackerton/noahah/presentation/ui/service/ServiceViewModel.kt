package com.hackerton.noahah.presentation.ui.service

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor() : ViewModel() {

    private var pdfMultiPart: MultipartBody.Part? = null

    fun setPdfMultiPart(part: MultipartBody.Part) {
        pdfMultiPart = part
    }

    fun getPdfMultiPart() = pdfMultiPart

}