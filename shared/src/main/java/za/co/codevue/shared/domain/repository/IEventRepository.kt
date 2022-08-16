package za.co.codevue.shared.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import za.co.codevue.shared.models.domain.Event

interface IEventRepository {
    suspend fun getEvent(eventId: String): Event
    fun getEvents(): Flow<PagingData<Event>>
}