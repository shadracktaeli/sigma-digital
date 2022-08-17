package za.co.codevue.shared.domain.datasource

import za.co.codevue.shared.models.network.EventDTO

internal interface IRemoteEventDataSource {
    fun fetchEvents(refresh: Boolean = false): List<EventDTO>
}