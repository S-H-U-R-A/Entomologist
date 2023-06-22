package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.repository.EntomologistRepositoryImpl
import com.pragma.entomologistapp.data.repository.ImageRepositoryImpl
import com.pragma.entomologistapp.data.repository.InsectRepositoryImpl
import com.pragma.entomologistapp.data.repository.LocationRepositoryImpl
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import com.pragma.entomologistapp.domain.repository.InsectRepository
import com.pragma.entomologistapp.domain.repository.LocationRepository
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

    @Binds
    abstract fun bindInsectRepository(
        insectRepositoryImpl: InsectRepositoryImpl
    ) : InsectRepository

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ) : ImagesRepository

    @Binds
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ) : LocationRepository

}