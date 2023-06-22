package com.pragma.entomologistapp.ui.countInsect

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pragma.entomologistapp.domain.model.InsectDomain
import com.pragma.entomologistapp.domain.usecases.app.GetCurrentLocationAppUseCase
import com.pragma.entomologistapp.domain.usecases.app.GetServicesLocationAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountInsectViewModel @Inject constructor(
    private val getServicesLocationAppUseCase: GetServicesLocationAppUseCase,
    private val getCurrentLocationAppUseCase: GetCurrentLocationAppUseCase
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

    fun sendMessageEntomologist( message: LocationMessage ){
        _uiState.update { actualSate ->
            val messageError: List<LocationMessage> =
                actualSate.messageEntomologist + message

            actualSate.copy(
                messageEntomologist = messageError
            )
        }
    }

    fun deleteMessageEntomologist( message: LocationMessage ){
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

    fun getServicesLocationInfoApp( action: ( isLocationUsable: Boolean ) -> Unit ){
        viewModelScope.launch {
            //SE OBTIENE INFORMACIÓN DE DISPONIBILIDAD DEL HARDWARE PARA OBTENER LA UBICACIÓN
            val isLocationEnabled =  getServicesLocationAppUseCase()
            //SE EJECUTA EL CÓDIGO
            action( isLocationEnabled )
        }
    }

    private suspend fun getCurrentLocationComplete(): Triple<Double, Double, String>?{
        val currentLocationComplete: Deferred<Triple<Double, Double, String>?> = viewModelScope.async {
            getCurrentLocationAppUseCase()
        }
        return  currentLocationComplete.await()
    }

    //FALTAN DATOS, ID INSECTO, ID USUARIO, CONTEO, COMENTARIOS
    fun enterInsectCount(){
        viewModelScope.launch {
            val dataLocationComplete = getCurrentLocationComplete()
            if(dataLocationComplete != null){
                //EMPEZAMOS EL PROCESO DE GUARDADAR LA INFO


            }else {
                //LE INDICAMOS AL USUARIO QUE HUBO UN ERROR AL OBTENER LA LOCALIZACIÓN Y QUE LO INTENTE DE NUEVO
                sendMessageEntomologist( LocationMessage.GET_LOCATION_ERROR )
            }

        }
    }


}

data class CountInsectUiState (
    val isLoading: Boolean = false,
    val insect: InsectDomain? = null,
    val countInsect: Int = 0,
    var messageEntomologist: List<LocationMessage> = emptyList()
)

//VALOR DE ESTADO QUE DEPENDE DE OTRO VALOR
val CountInsectUiState.isVisibleButtonSave: Boolean get() = countInsect > 0
val CountInsectUiState.isVisibleComment: Boolean get() = countInsect > 0


