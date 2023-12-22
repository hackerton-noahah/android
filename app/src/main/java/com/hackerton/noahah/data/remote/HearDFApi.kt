package com.hackerton.noahah.data.remote

import com.hackerton.noahah.data.model.request.GetFileUrlRequest
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
    ): Response<String>

    @POST("/pdfs")
    suspend fun registerPdf(
        @Body params: String
    ): Response<RegisterPdfResponse>

    @POST("/pdfs/text")
    suspend fun changePdfToText(
        @Body params: String
    ): Response<Unit>

    @GET("/pdfs/{id}")
    suspend fun getPdfText(
        @Path("id") id: Int
    ): Response<String>
}