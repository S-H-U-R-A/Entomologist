package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.repository.EntomologistRepositoryImpl
import com.pragma.entomologistapp.data.repository.GeolocationRepositoryImpl
import com.pragma.entomologistapp.data.repository.ImageRepositoryImpl
import com.pragma.entomologistapp.data.repository.InsectRepositoryImpl
import com.pragma.entomologistapp.data.repository.RecordInsectGeolocationRepositoryImpl
import com.pragma.entomologistapp.data.repository.RecordRepositoryImpl
import com.pragma.entomologistapp.data.repository.ServiceLocationRepositoryImpl
import com.pragma.entomologistapp.domain.repository.EntomologistRepository
import com.pragma.entomologistapp.domain.repository.GeolocationRepository
import com.pragma.entomologistapp.domain.repository.ImagesRepository
import com.pragma.entomologistapp.domain.repository.InsectRepository
import com.pragma.entomologistapp.domain.repository.RecordInsectGeolocationRepository
import com.pragma.entomologistapp.domain.repository.RecordRepository
import com.pragma.entomologistapp.domain.repository.ServiceLocationRepository
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
    abstract fun bindGeolocationRepository(
        geolocationRepositoryImpl: GeolocationRepositoryImpl
    ) : GeolocationRepository

    @Binds
    abstract fun bindRecordRepository(
        recordRepositoryImpl: RecordRepositoryImpl
    ) : RecordRepository

    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ) : ImagesRepository

    @Binds
    abstract fun bindLocationRepository(
        serviceLocationRepositoryImpl: ServiceLocationRepositoryImpl
    ) : ServiceLocationRepository

    @Binds
    abstract fun bindRecordInsectGeolocation(
        recordInsectGeolocationRepositoryImpl: RecordInsectGeolocationRepositoryImpl
    ) : RecordInsectGeolocationRepository

}