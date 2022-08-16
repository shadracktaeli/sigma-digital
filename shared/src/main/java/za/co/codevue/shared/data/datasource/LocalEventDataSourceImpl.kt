package za.co.codevue.shared.data.datasource

import androidx.paging.PagingSource
import za.co.codevue.shared.domain.datasource.ILocalEventDataSource
import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.persistence.room.IEventDao

internal class LocalEventDataSourceImpl(
    private val dao: IEventDao
) : ILocalEventDataSource {
    override suspend fun deleteEvents() = dao.deleteEvents()

    override suspend fun getEvent(eventId: String): EventEntity = dao.getEvent(eventId)

    override fun getEvents(): PagingSource<Int, EventEntity> = dao.getEvents()

    override suspend fun saveEvents(events: List<EventEntity>) = dao.saveEvents(events)
}