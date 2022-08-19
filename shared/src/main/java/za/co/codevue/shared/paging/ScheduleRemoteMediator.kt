package za.co.codevue.shared.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import za.co.codevue.shared.domain.datasource.ILocalScheduleDataSource
import za.co.codevue.shared.domain.datasource.IRemoteScheduleDataSource
import za.co.codevue.shared.models.entities.ScheduleEntity
import za.co.codevue.shared.models.mappers.toScheduleEntity

internal class ScheduleRemoteMediator(
    private val localDataSource: ILocalScheduleDataSource,
    private val remoteDataSource: IRemoteScheduleDataSource
) : RemoteMediator<Int, ScheduleEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ScheduleEntity>
    ): MediatorResult {
        return try {
            // report end of pagination
            if ((loadType == LoadType.PREPEND) || (loadType == LoadType.APPEND && state.lastItemOrNull() == null)) {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            // fetch events from api
            val schedules = remoteDataSource.fetchSchedules(refresh = loadType == LoadType.REFRESH)

            // clear schedules if we're refreshing
            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteSchedules()
            }
            // save new schedules to the cache
            localDataSource.saveSchedules(schedules.map { it.toScheduleEntity() })

            MediatorResult.Success(
                endOfPaginationReached = schedules.isEmpty() // report end of pagination if empty
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}