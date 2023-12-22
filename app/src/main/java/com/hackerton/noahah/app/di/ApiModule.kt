package com.hackerton.noahah.app.di

import com.hackerton.noahah.data.remote.HearDFApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHearDFApi(retrofit: Retrofit): HearDFApi {
        return retrofit.create(HearDFApi::class.java)
    }

}