package za.co.codevue.shared.data.datasource

import timber.log.Timber
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.paging.PagingConstants

internal abstract class RemoteDataSourceDelegate {
    var currentPage = 0
        private set
    var lastId: Int = 0
        private set

    /**
     * Simulates a paginating Api request
     */
    protected fun simulatePagination(
        refresh: Boolean,
        apiCall: () -> List<EventDTO>
    ): List<EventDTO> {
        Timber.e("refresh: $refresh")
        Timber.e("lastItemId: $lastId")
        Timber.e("currentPage: $currentPage")
        if (refresh) {
            currentPage = 0
            lastId = 0
        }

        return if (currentPage < PagingConstants.PAGE_LIMIT) {
            currentPage++
            val events = apiCall().toMutableList()

            if (lastId == 0) {
                // initial load -> save last item's ID
                lastId = events.last().id?.toInt() ?: 0
                events
            } else {
                events.sanitizeEventIds(lastId).run {
                    lastId = first
                    second // return sanitized list
                }
            }
        } else emptyList()
    }

    /**
     * Sanitizes event IDs to simulate new events
     */
    private fun MutableList<EventDTO>.sanitizeEventIds(lastEventId: Int): Pair<Int, List<EventDTO>> {
        var lastId = lastEventId
        forEachIndexed { index, event ->
            // increment by 1 to simulate ID sequence
            lastId++
            this[index] = event.copy(id = lastId.toString())
        }
        return Pair(lastId, this)
    }
}