package com.hackerton.noahah.data.repository

import com.hackerton.noahah.data.model.BaseState
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import com.hackerton.noahah.data.model.runRemote
import com.hackerton.noahah.data.remote.HearDFApi
import javax.inject.Inject

class HearDfRepositoryImpl @Inject constructor(
    private val api: HearDFApi
) : HearDfRepository {

    override suspend fun getFileUrl(body: GetFileUrlRequest): BaseState<String> =
        runRemote { api.getFileUrl(body) }

    override suspend fun getPdfText(id: Int): BaseState<String> = runRemote { api.getPdfText(id) }

    override suspend fun registerPdf(body: String): BaseState<Unit> =
        runRemote { api.registerPdf(body) }

    override suspend fun changePdfToText(body: String): BaseState<Unit> =
        runRemote { api.changePdfToText(body) }
}