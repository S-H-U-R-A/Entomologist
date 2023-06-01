package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.datasource.AppStorageDataSourceImpl
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.entomologist.EntomologistDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.entomologist.EntomologistDataStorageDataSourceImpl
import com.pragma.entomologistapp.data.datasource.insect.InsectDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.interfaces.AppStorageDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.entomologist.EntomologistDataStorageDataSource
import com.pragma.entomologistapp.data.datasource.interfaces.insect.InsectDataBaseDataSource
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
        entomologistDataStorageDataSource: EntomologistDataStorageDataSourceImpl
    ) : EntomologistDataStorageDataSource

    @Binds
    abstract fun bindAppStorageDataSource(
        appStorageDataSource: AppStorageDataSourceImpl
    ) : AppStorageDataSource

    @Binds
    abstract fun bindInsectDataBaseDataSource(
        insectDataBaseDataSource: InsectDataBaseDataSourceImpl
    ) : InsectDataBaseDataSource

}