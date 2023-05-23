package com.pragma.entomologistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pragma.entomologistapp.data.local.database.dao.EntomologistDao
import com.pragma.entomologistapp.data.local.database.dao.GeolocationDao
import com.pragma.entomologistapp.data.local.database.dao.InsectDao
import com.pragma.entomologistapp.data.local.database.dao.RecordDao
import com.pragma.entomologistapp.data.local.database.entity.EntomologistEntity
import com.pragma.entomologistapp.data.local.database.entity.GeolocationEntity
import com.pragma.entomologistapp.data.local.database.entity.InsectEntity
import com.pragma.entomologistapp.data.local.database.entity.RecordEntity

@Database(
    entities = [
        EntomologistEntity::class,
        InsectEntity::class,
        GeolocationEntity::class,
        RecordEntity::class
    ],
    version = EntomologyDataBase.DATABASE_VERSION,
    exportSchema = false
)
abstract class EntomologyDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "entomology_database"
    }

    //Define Dao´s
    abstract fun entomologistDao(): EntomologistDao
    abstract fun insectDao(): InsectDao
    abstract fun recordDao(): RecordDao
    abstract fun geolocationDao (): GeolocationDao




}