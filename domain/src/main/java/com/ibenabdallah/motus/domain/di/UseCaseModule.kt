package com.ibenabdallah.motus.domain.di

import com.ibenabdallah.motus.domain.GetAllMotusUseCase
import com.ibenabdallah.motus.domain.GetAllMotusUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCaseModule {

    @Binds
    fun bindGetAllMotusUseCase(impl: GetAllMotusUseCaseImpl): GetAllMotusUseCase
}