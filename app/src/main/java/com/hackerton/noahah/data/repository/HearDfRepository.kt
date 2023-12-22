package com.hackerton.noahah.data.repository

import com.hackerton.noahah.data.model.BaseState
import com.hackerton.noahah.data.model.request.ChangePdfToTextRequest
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import com.hackerton.noahah.data.model.request.RegisterPdfRequest
import com.hackerton.noahah.data.model.response.GetFileUrlResponse
import com.hackerton.noahah.data.model.response.RegisterPdfResponse

interface HearDfRepository {


    suspend fun getFileUrl(
        body: GetFileUrlRequest
    ): BaseState<GetFileUrlResponse>

    suspend fun registerPdf(
        body: RegisterPdfRequest
    ): BaseState<RegisterPdfResponse>

    suspend fun changePdfToText(
        body: ChangePdfToTextRequest
    ): BaseState<Unit>

    suspend fun getPdfText(
        id: Int
    ): BaseState<String>

}