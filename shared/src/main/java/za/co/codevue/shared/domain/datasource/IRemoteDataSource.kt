package za.co.codevue.shared.domain.datasource

import za.co.codevue.shared.models.network.EventDTO

internal interface IRemoteDataSource {
    fun fetchEvents(refresh: Boolean = false): List<EventDTO>
    fun fetchSchedules(refresh: Boolean = false): List<EventDTO>
}