package com.hackerton.noahah.data.repository

import com.hackerton.noahah.data.model.BaseState
import com.hackerton.noahah.data.model.request.ChangePdfToTextRequest
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import com.hackerton.noahah.data.model.request.RegisterPdfRequest
import com.hackerton.noahah.data.model.response.GetFileUrlResponse
import com.hackerton.noahah.data.model.response.RegisterPdfResponse
import com.hackerton.noahah.data.model.runRemote
import com.hackerton.noahah.data.remote.HearDFApi
import javax.inject.Inject

class HearDfRepositoryImpl @Inject constructor(
    private val api: HearDFApi
) : HearDfRepository {

    override suspend fun getFileUrl(body: GetFileUrlRequest): BaseState<GetFileUrlResponse> =
        runRemote { api.getFileUrl(body) }

    override suspend fun registerPdf(body: RegisterPdfRequest): BaseState<RegisterPdfResponse> =
        runRemote { api.registerPdf(body) }

    override suspend fun getPdfText(id: Int): BaseState<String> = runRemote { api.getPdfText(id) }

    override suspend fun changePdfToText(body: ChangePdfToTextRequest): BaseState<Unit> =
        runRemote { api.changePdfToText(body) }
}