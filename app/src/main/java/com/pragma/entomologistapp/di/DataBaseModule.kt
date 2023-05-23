package com.pragma.entomologistapp.di

import android.content.Context
import androidx.room.Room
import com.pragma.entomologistapp.data.local.EntomologyDataBase
import com.pragma.entomologistapp.data.local.database.dao.EntomologistDao
import com.pragma.entomologistapp.data.local.database.dao.GeolocationDao
import com.pragma.entomologistapp.data.local.database.dao.InsectDao
import com.pragma.entomologistapp.data.local.database.dao.RecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ): EntomologyDataBase {
        return synchronized(context){
            Room.databaseBuilder(
                context,
                EntomologyDataBase::class.java,
                EntomologyDataBase.DATABASE_NAME
            ).build()
        }
    }

    //Provide Dao's
    @Singleton
    @Provides
    fun provideEntomologistDao(dataBase: EntomologyDataBase): EntomologistDao{
        return dataBase.entomologistDao()
    }

    @Singleton
    @Provides
    fun provideInsectDao(dataBase: EntomologyDataBase): InsectDao{
        return dataBase.insectDao()
    }

    @Singleton
    @Provides
    fun provideGeolocationDao(dataBase: EntomologyDataBase): GeolocationDao{
        return dataBase.geolocationDao()
    }

    @Singleton
    @Provides
    fun provideRecordDao(dataBase: EntomologyDataBase): RecordDao{
        return dataBase.recordDao()
    }

}