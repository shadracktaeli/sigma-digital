package za.co.codevue.shared.data.datasource

import za.co.codevue.shared.domain.datasource.IRemoteDataSource
import za.co.codevue.shared.extensions.callApi
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.network.retrofit.service.IEventApiService
import za.co.codevue.shared.paging.PagingConstants

internal class RemoteDataSourceImpl(
    private val api: IEventApiService
) : IRemoteDataSource {
    private var currentPage = PagingConstants.INITIAL_PAGE
    private var lastEventId: Int = 0

    override fun fetchEvents(refresh: Boolean): List<EventDTO> = simulatePagination(refresh) {
        api.getEvents().callApi()
    }

    override fun fetchSchedules(refresh: Boolean): List<EventDTO> = simulatePagination(refresh) {
        api.getSchedule().callApi()
    }

    private fun simulatePagination(
        refresh: Boolean,
        apiCall: () -> List<EventDTO>
    ): List<EventDTO> {
        if (refresh) {
            currentPage = PagingConstants.INITIAL_PAGE
        }

        return if (currentPage < PagingConstants.PAGE_LIMIT) {
            currentPage++
            val events = apiCall().toMutableList()
            if (lastEventId == 0) {
                // initial load -> save last item's ID
                lastEventId = events.last().id?.toInt() ?: PagingConstants.INITIAL_PAGE
                events
            } else {
                events.sanitizeEventIds(lastEventId).run {
                    lastEventId = first
                    second // return sanitized list
                }
            }
        } else emptyList()
    }
}

private fun MutableList<EventDTO>.sanitizeEventIds(lastEventId: Int): Pair<Int, List<EventDTO>> {
    var lastId = lastEventId
    forEachIndexed { index, event ->
        // increment by 1 to simulate ID sequence
        lastId++
        this[index] = event.copy(id = lastId.toString())
    }
    return Pair(lastId, this)
}