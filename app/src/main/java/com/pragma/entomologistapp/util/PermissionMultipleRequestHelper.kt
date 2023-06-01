package com.pragma.entomologistapp.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionMultipleRequestHelper <T: Fragment>(
    fragment: T,
    private val permissions: Array<String>,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
) {

    //Lambda de, que hacer si todo fue exitoso
    private var onGranted: () -> Unit = {}

    // Esto validará si el permiso fue dado
    private val multiplePermission: ActivityResultLauncher< Array<String> > =

        //Esto retorna un objeto, de acciones a realizar cuando se valida un permiso solicitado
        fragment.registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) { mapPermission ->
            for( ( permission, granted ) in mapPermission){
                when {
                    granted -> onGranted()
                    fragment.shouldShowRequestPermissionRationale( permission ) -> onShowRationale()
                    else -> onDenied()
                }
            }
        }

    fun runWithPermission(onGranted: () -> Unit) {
        this.onGranted = onGranted
        multiplePermission.launch( permissions )
    }

}