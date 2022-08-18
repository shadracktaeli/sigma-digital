package za.co.codevue.sigmadigital.ui.events.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import za.co.codevue.shared.domain.statefuldata.StatefulData
import za.co.codevue.shared.domain.usecase.events.GetEventByIdUseCase
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Idle)
    val uiState = _uiState.asStateFlow()

    var playerState: PlayerState? = null

    fun getEvent(eventId: String) {
        viewModelScope.launch {
            _uiState.value = when (val result = getEventByIdUseCase(eventId)) {
                is StatefulData.Success -> EventDetailUiState.Success(result.data)
                is StatefulData.Error -> EventDetailUiState.Error(result.message)
                else -> EventDetailUiState.Idle
            }
        }
    }
}