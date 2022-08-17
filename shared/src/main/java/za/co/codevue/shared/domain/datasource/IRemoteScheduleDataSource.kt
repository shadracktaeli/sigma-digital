package za.co.codevue.shared.domain.datasource

import za.co.codevue.shared.models.network.EventDTO

internal interface IRemoteScheduleDataSource {
    fun fetchSchedules(refresh: Boolean = false): List<EventDTO>
}