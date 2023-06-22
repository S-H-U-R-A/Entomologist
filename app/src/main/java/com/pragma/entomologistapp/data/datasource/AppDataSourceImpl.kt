package com.pragma.entomologistapp.data.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Tasks
import com.pragma.entomologistapp.core.TypeUser
import com.pragma.entomologistapp.core.ext.checkLocationServicesEnabled
import com.pragma.entomologistapp.core.ext.savePhotoInExternalStorage
import com.pragma.entomologistapp.core.ext.toBitmap
import com.pragma.entomologistapp.data.datasource.interfaces.AppDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class AppDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : AppDataSource {

    override suspend fun savePhotoInExternalStorage(
        uriImage: Uri,
        type: TypeUser,
        nameInsect: String?
    ): String? {
        return uriImage.toBitmap(context)?.savePhotoInExternalStorage(
            context,
            type,
            nameInsect
        )
    }


    override suspend fun checkLocationServicesEnabled(): Boolean {
        return context.checkLocationServicesEnabled()
        //TODO("REFACTORIZAR ESTO PARA NO USAR UNA FUNCIÓN DE EXTENSIÓN, EN LUGAR USAR UNA CLASE HELPER VALIDATE LOCATON")
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLocationCoordinates(): Location? {
        //SE USA EL CLIENTE DE UBICACIÓN FUSIONADA
        val taskLocation  = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        //FUNCIONA COMO UNA FUNCIÓN SUSPEND
        Tasks.await( taskLocation )
        //SI LA TASK FUE EXITOSA Y TENEMOS LA UBICACIÓN
        if (taskLocation.isSuccessful && taskLocation.result != null){
            //SE RETORNA EL OBJETO LOCATION QUE TIENE LAS COORDENADAS
            return taskLocation.result
        }
        return null
    }

    override suspend fun getPlaceNameFromCoordinates(location: Location): String{

        val deferred = CompletableDeferred<String>()

        Geocoder(context).getFromLocation(
            location.latitude,
            location.longitude,
            1
        ) { listAddress: MutableList<Address> ->

            deferred.complete( listAddress.first().locality ?: "Sin Ciudad" )

        }

        return deferred.await()

    }

}