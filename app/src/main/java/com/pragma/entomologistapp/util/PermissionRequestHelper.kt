package com.pragma.entomologistapp.util

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

//Clase auxiliar que permite solicitar los permisos
class PermissionRequestHelper<T: ComponentActivity>(
    fragment: T,
    private val permission: String,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
) {

    //Lambda de, que hacer si todo fue exitoso
    private var onGranted: () -> Unit = {}

    // Esto validará si el permiso fue dado
    private val onlyPermission =
        //Esto retorna un objeto, de acciones a realizar cuando se valida un permiso solicitado
        fragment.registerForActivityResult( ActivityResultContracts.RequestPermission() ) { isGranted ->
            when {
                isGranted -> onGranted()
                fragment.shouldShowRequestPermissionRationale( permission ) -> onShowRationale()
                else -> onDenied()
            }
        }

    fun runWithPermission(onGranted: () -> Unit) {
        this.onGranted = onGranted
        onlyPermission.launch( permission )
    }
}