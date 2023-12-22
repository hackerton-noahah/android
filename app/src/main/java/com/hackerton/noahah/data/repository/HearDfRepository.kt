package com.hackerton.noahah.data.repository

import com.hackerton.noahah.data.model.BaseState
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import java.util.stream.BaseStream

interface HearDfRepository {


    suspend fun getFileUrl(
        body: GetFileUrlRequest
    ): BaseState<String>

    suspend fun registerPdf(
        body: String
    ): BaseState<Int>

    suspend fun changePdfToText(
        body: String
    ): BaseState<Unit>

    suspend fun getPdfText(
        id: Int
    ): BaseState<String>

}