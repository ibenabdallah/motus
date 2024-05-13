package com.ibenabdallah.motus.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Qualifier
annotation class ViewModelCoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @ViewModelCoroutineContext
    fun provideViewModelCoroutineContext(): CoroutineContext =
        Dispatchers.Main + CoroutineName("ViewModel")
}
