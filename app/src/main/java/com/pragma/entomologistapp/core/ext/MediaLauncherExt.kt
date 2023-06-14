package com.pragma.entomologistapp.core.ext

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

fun Fragment.pickMediaLauncher( action:(uri: Uri?) -> Unit ): ActivityResultLauncher<PickVisualMediaRequest>  {
    return registerForActivityResult( ActivityResultContracts.PickVisualMedia() ) { uri ->
        action(uri)
    }
}
