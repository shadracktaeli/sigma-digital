package za.co.codevue.sigmadigital.ui.events.detail

import za.co.codevue.shared.models.domain.Event

sealed class EventDetailUiState {
    object Idle : EventDetailUiState()
    data class Success(val event: Event) : EventDetailUiState()
    data class Error(val message: String) : EventDetailUiState()
}