package mocks

import za.co.codevue.shared.domain.datasource.IRemoteEventDataSource
import za.co.codevue.shared.models.network.EventDTO

internal class MockEventRemoteDataSource : IRemoteEventDataSource {
    private val mockEvents: MutableList<EventDTO> = mutableListOf()

    var throwError = false

    override fun fetchEvents(refresh: Boolean): List<EventDTO> {
        if (throwError) throw Exception("error")
        return mockEvents
    }

    fun addEvent(event: EventDTO) {
        mockEvents.add(event)
    }
}