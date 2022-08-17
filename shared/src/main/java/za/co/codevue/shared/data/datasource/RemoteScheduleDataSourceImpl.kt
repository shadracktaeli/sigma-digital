package za.co.codevue.shared.data.datasource

import za.co.codevue.shared.domain.datasource.IRemoteScheduleDataSource
import za.co.codevue.shared.extensions.callApi
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.network.retrofit.service.IEventApiService

internal class RemoteScheduleDataSourceImpl(
    private val api: IEventApiService
) : RemoteDataSourceDelegate(), IRemoteScheduleDataSource {

    override fun fetchSchedules(refresh: Boolean): List<EventDTO> = simulatePagination(refresh) {
        api.getSchedule().callApi()
    }
}