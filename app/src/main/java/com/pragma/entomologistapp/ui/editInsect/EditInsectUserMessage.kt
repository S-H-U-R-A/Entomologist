package com.pragma.entomologistapp.ui.editInsect

import androidx.annotation.StringRes
import com.pragma.entomologistapp.R

enum class EditInsectUserMessage(
    val code: Int,
    @StringRes val message: Int
) {
    NOT_DATA_FOUND_FOR_EDIT(1, R.string.not_data_found_for_edit)
}