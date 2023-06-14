package com.pragma.entomologistapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @IoDispatcher
    @Provides
    fun provideDispatcherIo() : CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun provideDispatcherDefault() : CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun provideDispatcherMain() : CoroutineDispatcher = Dispatchers.Main


}