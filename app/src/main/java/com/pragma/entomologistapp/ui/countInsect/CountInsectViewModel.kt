package com.pragma.entomologistapp.ui.countInsect

import androidx.lifecycle.ViewModel
import com.pragma.entomologistapp.domain.model.InsectDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class CountInsectViewModel @Inject constructor(

) : ViewModel() {

    private var _uiState: MutableStateFlow<CountInsectUiState> = MutableStateFlow(CountInsectUiState())
    val uiState: StateFlow<CountInsectUiState> get() = _uiState.asStateFlow()

    fun plusInsect(){
        _uiState.update { state ->
            val countInsectPlus = state.countInsect.plus(1)
            state.copy(
                countInsect = countInsectPlus
            )
        }
    }

    fun minusInsect(){
        _uiState.update { state ->
            val countInsectMinus = if(state.countInsect > 0) state.countInsect.minus(1) else state.countInsect
            state.copy(
                countInsect = countInsectMinus
            )
        }
    }

}

data class CountInsectUiState(
    val insect: InsectDomain? = null,
    val countInsect: Int = 0
)

//VALOR DE ESTADO QUE DEPENDE DE OTRO VALOR
val CountInsectUiState.isVisibleButtonSave: Boolean get() = countInsect > 0
val CountInsectUiState.isVisibleComment: Boolean get() = countInsect > 0