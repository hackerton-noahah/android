package com.hackerton.noahah.app.di

import com.hackerton.noahah.data.repository.HearDfRepository
import com.hackerton.noahah.data.repository.HearDfRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindHearDfRepository(
        hearDfRepositoryImpl: HearDfRepositoryImpl
    ): HearDfRepository

}