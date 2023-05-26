package com.pragma.entomologistapp.di

import com.pragma.entomologistapp.data.datasource.interfaces.EntomologistDataBaseDataSource
import com.pragma.entomologistapp.data.datasource.EntomologistDataBaseDataSourceImpl
import com.pragma.entomologistapp.data.datasource.EntomologistDataStorageDataSourceImpl
import com.pragma.entomologistapp.data.datasource.interfaces.EntomologistDataStorageDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    ): EntomologistDataStorageDataSource

}