package com.hackerton.noahah.data.model.request

import okhttp3.MultipartBody

data class GetFileUrlRequest(
    val file: MultipartBody.Part
)
