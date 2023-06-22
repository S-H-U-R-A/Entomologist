package com.pragma.entomologistapp.data.datasource.interfaces

import android.location.Location
import android.net.Uri
import com.pragma.entomologistapp.core.TypeUser


interface AppDataSource {
    suspend fun savePhotoInExternalStorage(
        uriImage: Uri,
        type: TypeUser,
        nameInsect:String?
    ): String?

    suspend fun checkLocationServicesEnabled() : Boolean

    suspend fun getLocationCoordinates() : Location?

    suspend fun getPlaceNameFromCoordinates(location: Location): String

}