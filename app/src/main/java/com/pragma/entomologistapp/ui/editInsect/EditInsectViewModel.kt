package com.pragma.entomologistapp.ui.editInsect

import androidx.lifecycle.ViewModel
import com.pragma.entomologistapp.domain.model.RecordInsectGeolocationDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditInsectViewModel @Inject constructor(): ViewModel(){

    private var _uiState: MutableStateFlow<EditInsectUiState> = MutableStateFlow(EditInsectUiState());
    public val uiState: StateFlow<EditInsectUiState> = _uiState.asStateFlow();

    fun setInsectWithExtraData( insect: RecordInsectGeolocationDomain ){
        _uiState.update {
            it.copy(
                insectWithExtraData = insect
            )
        }
    }

    fun sendMessageEntomologist( message: EditInsectUserMessage ){
        _uiState.update { actualSate ->
            val messageError: List<EditInsectUserMessage> =
                actualSate.messageEntomologist + message

            actualSate.copy(
                messageEntomologist = messageError
            )
        }
    }

    fun deleteMessageEntomologist( message: EditInsectUserMessage ){
        _uiState.update { actualState ->
            val newListWithOutMessage =
                actualState.messageEntomologist.filterNot {
                    it.code == message.code
                }
            actualState.copy(
                messageEntomologist = newListWithOutMessage
            )
        }
    }

    // TODO: DESARROLLAR LÓGICA DE CARGA DE MAPA DE GOOGLE MAPS 
}

data class EditInsectUiState(
    val insectWithExtraData: RecordInsectGeolocationDomain? = null,
    var messageEntomologist: List<EditInsectUserMessage> = emptyList()
)