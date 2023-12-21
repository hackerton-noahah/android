package com.hackerton.noahah.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

//    @Singleton
//    @Provides
//    fun provideIntroService(retrofit: Retrofit): IntroAPI {
//        return retrofit.create(IntroAPI::class.java)
//    }

}