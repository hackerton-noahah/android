package com.hackerton.noahah.data.remote

import com.hackerton.noahah.data.model.request.ChangePdfToTextRequest
import com.hackerton.noahah.data.model.request.GetFileUrlRequest
import com.hackerton.noahah.data.model.request.RegisterPdfRequest
import com.hackerton.noahah.data.model.response.GetFileUrlResponse
import com.hackerton.noahah.data.model.response.RegisterPdfResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HearDFApi {

    @POST("/files")
    suspend fun getFileUrl(
        @Body params: GetFileUrlRequest
    ): Response<GetFileUrlResponse>

    @POST("/pdfs")
    suspend fun registerPdf(
        @Body params: RegisterPdfRequest
    ): Response<RegisterPdfResponse>

    @POST("/pdfs/text")
    suspend fun changePdfToText(
        @Body params: ChangePdfToTextRequest
    ): Response<Unit>

    @GET("/pdfs/{id}")
    suspend fun getPdfText(
        @Path("id") id: Int
    ): Response<String>
}