package com.pragma.entomologistapp.core.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.pragma.entomologistapp.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Int.formatTwoDigits(): String {
    return String.format("%02d", this)
}

//PODEMOS VALIDAR SI EL HARDWARE DE UBICACIÓN ESTA DISPONIBLE
fun Context.checkLocationServicesEnabled(): Boolean {

    //SE CREA COMO VA A SER NUESTRA SOLICITUD
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000
    ).build()

    //CONFIGURAMOS LA SOLICITUD DE VERIFICACIÓN DE CONFIGURACIONES DE UBICACIÓN
    val builderLocationSettings = LocationSettingsRequest.Builder()
        .addLocationRequest( locationRequest )
        .setAlwaysShow(true)
        .build() // Muestra un diálogo al usuario incluso si todas las configuraciones están habilitadas <- DEBO PROBARLO

    //SE CREA EL CLIENTE PARA LA VALIDACIÓN CON LA CONFIGURACIÓN ACTUAL
    val client: SettingsClient = LocationServices.getSettingsClient(this)

    //RESPUESTA DEL CLIENTE AL VERIFICAR LA DISPONIBILIDAD DE LA UBICACIÓN
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings( builderLocationSettings )

    try {

        //RETORNAMOS LA RESPUESTA DEL SISTEMA, ESTA ES UNA FUNCIÓN SUSPENDIBLE
        val locationSettingsResponse = Tasks.await( task )

        return locationSettingsResponse.locationSettingsStates?.isLocationPresent ?: false

    } catch (exception: Exception) {
        Log.d("ErrorLocation", exception.message.toString())
    }

    return false
}

fun Context.checkMultiplePermissionGranted(vararg permissions: String) : Boolean{
    permissions.forEach { permission ->
        if( ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED ) {
            return false
        }
    }
    return true
}

fun Context.checkPermissionGranted(permission: String) : Boolean{
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
}

fun Context.showSimpleMessageSnackBar(view: View, @StringRes message: Int){
    Snackbar.make(this, view, getString(message) , Snackbar.LENGTH_LONG).show()
}

fun Context.showSnackBarSettings(
    view: View,
    @StringRes message: Int,
    @StringRes actionMessage: Int,
    packageName: String
){
    Snackbar.make(
        this,
        view,
        getString(message),
        Snackbar.LENGTH_LONG
    ).setAction( actionMessage ) {
        //SE DIRIGE AL USUARIO A LOS AJUSTES DE LA APP
        Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS ).apply {
            addCategory( Intent.CATEGORY_DEFAULT )
            data = Uri.parse("package:$packageName")
        }.let(::startActivity)

    }.show()
}

fun Fragment.showOrHideDialogLoading(showDialog: Boolean){
    (this.requireActivity() as MainActivity).showOrHideDialogLoading(showDialog)
}


fun EditText.inputTypeWithImeOption(imeOpt: Int, inType: Int){
    imeOptions = imeOpt
    setRawInputType(inType)
    setHorizontallyScrolling(false)
}


fun Calendar.toStringWithFormat(format: String) : String{
    val dateFormat = SimpleDateFormat( format , Locale.getDefault())
    return dateFormat.format( this.time )
}

fun String.toCalendar(format: String) : Calendar{
    val dateFormat = SimpleDateFormat( format , Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(this)!!
    return calendar
}

