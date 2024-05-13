package com.ibenabdallah.motus.data.di

import com.ibenabdallah.motus.domain.repository.MotusRepository
import com.ibenabdallah.motus.data.MotusRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMotusRepository(impl: MotusRepositoryImpl): MotusRepository

}