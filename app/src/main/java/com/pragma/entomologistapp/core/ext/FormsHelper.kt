package com.pragma.entomologistapp.core.ext

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment


fun Fragment.validateFields( action: () -> Unit  ) : TextWatcher{
    return object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action()
        }
        override fun afterTextChanged(s: Editable?) {}
    }
}