package com.ibenabdallah.motus.data.di

import com.ibenabdallah.motus.data.remote.MotusRemoteRetriever
import com.ibenabdallah.motus.data.remote.MotusRemoteRetrieverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RetrieverModule {

    @Binds
    @Singleton
    fun bindMotusRetriever(impl: MotusRemoteRetrieverImpl): MotusRemoteRetriever

}