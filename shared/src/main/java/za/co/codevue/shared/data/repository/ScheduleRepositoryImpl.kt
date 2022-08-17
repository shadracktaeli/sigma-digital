package za.co.codevue.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import za.co.codevue.shared.domain.datasource.ILocalScheduleDataSource
import za.co.codevue.shared.domain.datasource.IRemoteScheduleDataSource
import za.co.codevue.shared.domain.repository.IScheduleRepository
import za.co.codevue.shared.models.domain.Schedule
import za.co.codevue.shared.models.mappers.toSchedule
import za.co.codevue.shared.paging.PagingConstants
import za.co.codevue.shared.paging.ScheduleRemoteMediator

internal class ScheduleRepositoryImpl(
    private val localDataSource: ILocalScheduleDataSource,
    private val remoteDataSource: IRemoteScheduleDataSource
) : IScheduleRepository {
    override fun getSchedules(): Flow<PagingData<Schedule>> {
        return Pager(
            PagingConfig(
                pageSize = PagingConstants.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PagingConstants.PRE_FETCH_DISTANCE
            ),
            remoteMediator = ScheduleRemoteMediator(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            ),
            pagingSourceFactory = { localDataSource.getSchedules() }
        ).flow.map { pagingData -> pagingData.map { it.toSchedule() } }
    }
}