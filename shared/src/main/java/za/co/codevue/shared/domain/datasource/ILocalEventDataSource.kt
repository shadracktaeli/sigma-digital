package za.co.codevue.shared.domain.datasource

import androidx.paging.PagingSource
import za.co.codevue.shared.models.entities.EventEntity

internal interface ILocalEventDataSource {
    suspend fun deleteEvents()
    suspend fun getEvent(eventId: String): EventEntity
    fun getEvents(): PagingSource<Int, EventEntity>
    suspend fun saveEvents(events: List<EventEntity>)
}