package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.repository.EntomologistRepositoryImpl
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEntomologistRepository(
        entomologistRepositoryImpl: EntomologistRepositoryImpl
    ) : EntomologistRepository

}