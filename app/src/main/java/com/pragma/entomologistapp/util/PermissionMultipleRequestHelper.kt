package com.pragma.entomologistapp.util

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionMultipleRequestHelper <T: Fragment>(
    fragment: T,
    private val permissions: Array<String>,
    onDenied: ( permission: String ) -> Unit = {},
    onShowRationale: ( permission: String ) -> Unit = {},
) {
    //Lambda de, que hacer si fue exitoso
    private var onGranted: () -> Unit = {}
    // Esto validará si el permiso fue dado
    private val multiplePermission: ActivityResultLauncher< Array< String > > =
        //Esto retorna un objeto, de acciones a realizar cuando se valida un permiso solicitado
        fragment.registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) { mapPermission ->

            for( ( permission, granted ) in mapPermission ){
                when {
                    //ESTA VALIDACIÓN ES UN BOOLEAN
                    granted -> onGranted()
                    //ESTA VALIDACIÓN ES UN BOOLEAN
                    //LA PREGUNTA ES DEBO MOSTRAR MENSAJE DE USUARIO  PARA EL PERMISO PASADO COMO
                    //PARÁMETRO A ESTA FUNCIÓN
                    fragment.shouldShowRequestPermissionRationale( permission ) -> onShowRationale( permission )
                    //POR ÚLTIMO NO CONCEDIERON EL PERMISO
                    else -> onDenied( permission )
                }
            }
        }

    fun runWithPermission(onGranted: () -> Unit) {
        // QUE HACER SI NOS OTORGARÖN EL PERMISO
        this.onGranted = onGranted
        //SE LANZA LA SOLICITUD DEL PERMISO
        multiplePermission.launch( permissions )
    }

}