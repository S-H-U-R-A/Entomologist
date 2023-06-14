package com.pragma.entomologistapp.core.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.pragma.entomologistapp.core.TypeUser
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Uri.toBitmap(context: Context): Bitmap? {
    var bitmap: Bitmap? = null
    bitmap = try {
        //SE CREA EL STREAM
        val inputStream: InputStream? = context.contentResolver.openInputStream(this)
        //SE CREA EL BITMAP A PARTIR DEL STREAM
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
    return bitmap
}

fun Bitmap.savePhotoInExternalStorage(
    context: Context,
    type: TypeUser,
    nameInsect: String?
): String {

    //SE CREA EL PATH
    val directoryPath = if (type == TypeUser.USER) File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "entomologist")
                        else File( context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"insects")

    //VALIDAR SI EL DIRECTORIO EXISTE O LO DEBEMOS CREAR
    if (!directoryPath.exists()) directoryPath.mkdirs()

    //IDENTIFICADOR UNICO PARA LA IMAGEN
    val dateId: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Calendar.getInstance().time)

    //NOMBRE DEL ARCHIVO
    var fileName: String = "${type.names}_$dateId.jpg"

    if (nameInsect != null) {

        val nameAux = fileName.split("_")

        fileName = buildString {
            append(nameAux[0])
            append("_${nameInsect}_")
            append(nameAux[1])
            append(nameAux[2])
        }
    }

    //SE CREA EL ARCHIVO SIN NINGUNA INFO
    val file = File(directoryPath, fileName)

    //ABRIMOS EL ARCHIVO
    val outputStream = FileOutputStream(file)

    //ESCRIBIMOS LA IMAGEN EN EL ARCHIVO
    this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

    //VERIFICAMOS Y CERRAMOS EL ARCHIVO
    outputStream.flush()
    outputStream.close()

    //RETORNORNAMOS EL PATH
    return file.absolutePath

}