package mocks

import za.co.codevue.shared.domain.datasource.IRemoteScheduleDataSource
import za.co.codevue.shared.models.network.EventDTO

internal class MockScheduleRemoteDataSource : IRemoteScheduleDataSource {
    private val mockSchedules: MutableList<EventDTO> = mutableListOf()

    var throwError = false

    override fun fetchSchedules(refresh: Boolean): List<EventDTO> {
        if (throwError) throw Exception("error")
        return mockSchedules
    }

    fun addSchedule(schedule: EventDTO) {
        mockSchedules.add(schedule)
    }
}