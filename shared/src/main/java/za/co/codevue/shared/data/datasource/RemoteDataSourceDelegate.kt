package za.co.codevue.shared.data.datasource

import za.co.codevue.shared.extensions.modifyDate
import za.co.codevue.shared.extensions.valueOrDefault
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.paging.PagingConstants

internal abstract class RemoteDataSourceDelegate {
    var currentPage = 0
        private set
    var lastId: Int = 0
        private set
    var lastDate: String? = null
    private set

    /**
     * Simulates a paginating Api request
     */
    protected fun simulatePagination(
        refresh: Boolean,
        apiCall: () -> List<EventDTO>
    ): List<EventDTO> {
        if (refresh) {
            currentPage = 0
            lastId = 0
            lastDate = null
        }

        return if (currentPage < PagingConstants.PAGE_LIMIT) {
            currentPage++
            val events = apiCall().toMutableList()

            if (lastId == 0) {
                // initial load -> save last item's ID
                lastId = events.last().id?.toInt() ?: 0
                lastDate = events.last().date
                events
            } else {
                events.sanitizeEvents(lastId, lastDate.valueOrDefault()).run {
                    lastId = first
                    lastDate = second
                    third // return sanitized list
                }
            }
        } else emptyList()
    }

    /**
     * Sanitizes event IDs and dates to simulate new events
     */
    private fun MutableList<EventDTO>.sanitizeEvents(lastEventId: Int, lastEventDate: String): Triple<Int, String, List<EventDTO>> {
        var lastId = lastEventId
        val lastDate = lastEventDate.modifyDate()
        forEachIndexed { index, event ->
            // increment by 1 to simulate ID sequence
            lastId++
            this[index] = event.copy(id = lastId.toString())
        }
        return Triple(lastId, lastDate, this.map { it.copy(date = lastDate) })
    }
}