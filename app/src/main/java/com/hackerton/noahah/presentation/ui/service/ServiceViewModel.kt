package com.hackerton.noahah.presentation.ui.service

import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

class ServiceViewModel : ViewModel() {

    private var pdfMultiPart: MultipartBody.Part? = null

    fun setMultiPart(part: MultipartBody.Part) {
        pdfMultiPart = part
    }
}