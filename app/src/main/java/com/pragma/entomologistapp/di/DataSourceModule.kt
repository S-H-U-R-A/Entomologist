package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.datasource.AppDataSourceImpl
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.entomologist.EntomologistDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.entomologist.EntomologistDataStorageDataSourceImpl
import com.pragma.entomologistapp.data.datasource.geolocation.GeolocationDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.insect.InsectDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.interfaces.AppDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.geolocation.GeolocationDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.insect.InsectDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.record.RecordDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.record.RecordDataBaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindEntomologistDataBaseDataSource(
        entomologistDataBaseDataSourceImpl: EntomologistDataBaseDataSourceImpl
    ) : EntomologistDataBaseDataSource


    @Binds
    abstract fun bindEntomologistDataStorageDataSource(
        entomologistDataStorageDataSourceImpl: EntomologistDataStorageDataSourceImpl
    ) : EntomologistDataStorageDataSource

    @Binds
    abstract fun bindAppStorageDataSource(
        appDataSourceImpl: AppDataSourceImpl
    ) : AppDataSource

    @Binds
    abstract fun bindInsectDataBaseDataSource(
        insectDataBaseDataSourceImpl: InsectDataBaseDataSourceImpl
    ) : InsectDataBaseDataSource

    @Binds
    abstract fun bindGeolocationDataBaseDataSource(
        geolocationDataBaseDataSourceImpl: GeolocationDataBaseDataSourceImpl
    ) : GeolocationDataBaseDataSource

    @Binds
    abstract fun bindRecordDataBaseDataSource(
        recordDataBaseDataSourceImpl: RecordDataBaseDataSourceImpl
    ) : RecordDataBaseDataSource

}