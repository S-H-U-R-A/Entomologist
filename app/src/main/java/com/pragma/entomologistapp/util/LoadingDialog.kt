package com.pragma.entomologistapp.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.pragma.entomologistapp.R

class LoadingDialog  : DialogFragment(R.layout.loading_layout){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawable( ColorDrawable( Color.TRANSPARENT ) )
            requestWindowFeature( Window.FEATURE_NO_TITLE )
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }
}