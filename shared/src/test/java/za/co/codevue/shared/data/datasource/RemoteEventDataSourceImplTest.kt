package za.co.codevue.shared.data.datasource

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import za.co.codevue.shared.exceptions.ServerException
import za.co.codevue.shared.models.network.EventDTO
import za.co.codevue.shared.network.retrofit.service.IEventApiService
import za.co.codevue.shared.paging.PagingConstants

@RunWith(JUnit4::class)
class RemoteEventDataSourceImplTest : BaseRemoteDataSourceTest() {
    private val remoteDataSource = RemoteEventDataSourceImpl(
        createApiService(IEventApiService::class)
    )

    @Test
    fun `should return a list of events on 200 response`() {
        // given
        mockWebServer.enqueueResponse(fileName = EVENTS_RESPONSE, code = 200)
        // when
        val result = remoteDataSource.fetchEvents(refresh = false)
        // then
        assertTrue(result.isNotEmpty())
        assertTrue(result.first().videoUrl?.isNotBlank() == true)
    }

    @Test
    fun `should paginate events until page limit is reached`() {
        // given
        val events = mutableListOf<EventDTO>()
        queueActions(PagingConstants.PAGE_LIMIT) {
            mockWebServer.enqueueResponse(fileName = EVENTS_RESPONSE, code = 200)
        }
        assertTrue(remoteDataSource.currentPage == 0)
        assertTrue(remoteDataSource.lastId == 0)
        // when
        queueActions(PagingConstants.PAGE_LIMIT.plus(1)) {
            events.addAll(remoteDataSource.fetchEvents(refresh = false))
        }
        // then
        assertTrue(remoteDataSource.currentPage == PagingConstants.PAGE_LIMIT)
        assertTrue(remoteDataSource.lastId == events.last().id?.toInt())
    }

    @Test
    fun `should return an empty list when page limit is reached`() {
        // given
        val events = mutableListOf<EventDTO>()
        queueActions(PagingConstants.PAGE_LIMIT.plus(1)) {
            mockWebServer.enqueueResponse(fileName = EVENTS_RESPONSE, code = 200)
        }
        assertTrue(remoteDataSource.currentPage == 0)
        assertTrue(remoteDataSource.lastId == 0)
        // when
        for (i in 0 until PagingConstants.PAGE_LIMIT.plus(1)) {
            val result = remoteDataSource.fetchEvents(refresh = false)
            if (i == PagingConstants.PAGE_LIMIT) {
                events.addAll(result)
            }
        }
        // then
        assertTrue(remoteDataSource.currentPage == PagingConstants.PAGE_LIMIT)
        assertTrue(events.isEmpty())
    }

    @Test
    fun `should restart pagination when refresh is true`() {
        // given
        queueActions(times = PagingConstants.PAGE_LIMIT) {
            mockWebServer.enqueueResponse(fileName = EVENTS_RESPONSE, code = 200)
        }
        // when
        queueActions(PagingConstants.PAGE_LIMIT) {
            remoteDataSource.fetchEvents(refresh = false)
        }
        queueActions(times = 1) {
            mockWebServer.enqueueResponse(fileName = EVENTS_RESPONSE, code = 200)
        }
        val schedules = remoteDataSource.fetchEvents(refresh = true)
        // then
        assertTrue(remoteDataSource.currentPage == 1)
        assertTrue(schedules.isNotEmpty())
    }

    @Test(expected = ServerException::class)
    fun `should throw a server exception on 404 response`() {
        // given
        mockWebServer.enqueueResponse(fileName = ERROR_RESPONSE, code = 404)
        // when
        remoteDataSource.fetchEvents(refresh = false)
    }

    private companion object {
        const val EVENTS_RESPONSE = "events-success-response"
        const val ERROR_RESPONSE = "error-response"
    }
}