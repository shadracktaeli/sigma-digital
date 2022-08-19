package za.co.codevue.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import za.co.codevue.shared.domain.datasource.ILocalEventDataSource
import za.co.codevue.shared.domain.datasource.IRemoteEventDataSource
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.models.domain.Event
import za.co.codevue.shared.models.mappers.toEvent
import za.co.codevue.shared.paging.EventRemoteMediator
import za.co.codevue.shared.paging.PagingConstants

internal class EventRepositoryImpl(
    private val localDataSource: ILocalEventDataSource,
    private val remoteDataSource: IRemoteEventDataSource
) : IEventRepository {
    override suspend fun getEvent(eventId: String): Event {
        return localDataSource.getEvent(eventId).run {
            this.toEvent()
        }
    }

    override fun getEvents(): Flow<PagingData<Event>> {
        return Pager(
            PagingConfig(
                pageSize = PagingConstants.PAGE_SIZE,
                prefetchDistance = PagingConstants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PagingConstants.PAGE_SIZE * 2
            ),
            remoteMediator = EventRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            ),
            pagingSourceFactory = { localDataSource.getEvents() }
        ).flow.map { pagingData -> pagingData.map { it.toEvent() } }
    }
}