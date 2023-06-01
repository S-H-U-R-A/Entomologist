package com.pragma.entomologistapp.ui.registerInsect

import androidx.lifecycle.ViewModel
import com.pragma.entomologistapp.domain.model.InsectDomain
import dagger.hilt.android.lifecycle.HiltViewModel

//@HiltViewModel
class RegisterInsectViewModel() : ViewModel(){





}

data class RegisterInsectUiState(
    val imageEntomologist: String = "Sin foto",
    val insectList: List<InsectDomain> = emptyList(),
    val isVisibleList: Boolean = false,
    val isVisibleReport: Boolean = false,
    val isVisibleRecords: Boolean = false
)