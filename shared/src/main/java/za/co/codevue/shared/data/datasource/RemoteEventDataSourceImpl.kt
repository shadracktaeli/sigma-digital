package za.co.codevue.shared.data.datasource

import za.co.codevue.shared.domain.datasource.IRemoteEventDataSource
import za.co.codevue.shared.extensions.callApi
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.network.retrofit.service.IEventApiService

internal class RemoteEventDataSourceImpl(
    private val api: IEventApiService
) : RemoteDataSourceDelegate(), IRemoteEventDataSource {

    override fun fetchEvents(refresh: Boolean): List<EventDTO> = simulatePagination(refresh) {
        api.getEvents().callApi()
    }
}

