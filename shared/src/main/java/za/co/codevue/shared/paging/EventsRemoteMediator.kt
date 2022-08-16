package za.co.codevue.shared.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import za.co.codevue.shared.domain.datasource.ILocalEventDataSource
import za.co.codevue.shared.domain.datasource.IRemoteDataSource
import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.models.mappers.toEntity

internal class EventsRemoteMediator(
    private val localDataSource: ILocalEventDataSource,
    private val remoteDataSource: IRemoteDataSource
) : RemoteMediator<Int, EventEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        return try {
            // report end of pagination
            if ((loadType == LoadType.PREPEND) || (loadType == LoadType.APPEND && state.lastItemOrNull() == null)) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            // fetch events from api
            val events = remoteDataSource.fetchEvents(refresh = loadType == LoadType.REFRESH)
            if (events.isNotEmpty()) {
                // clear events if we're refreshing
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteEvents()
                }
                // save new events to the cache
                localDataSource.saveEvents(events.map { it.toEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = events.isEmpty() // report end of pagination if empty
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}