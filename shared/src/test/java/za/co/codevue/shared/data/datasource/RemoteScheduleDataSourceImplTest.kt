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
class RemoteScheduleDataSourceImplTest : BaseRemoteDataSourceTest() {
    private val remoteDataSource = RemoteScheduleDataSourceImpl(
        createApiService(IEventApiService::class)
    )

    @Test
    fun `should return a list of schedules on 200 response`() {
        // given
        mockWebServer.enqueueResponse(fileName = SCHEDULES_RESPONSE, code = 200)
        // when
        val result = remoteDataSource.fetchSchedules(refresh = false)
        // then
        assertTrue(result.isNotEmpty())
        assertTrue(result.first().videoUrl == null)
    }

    @Test
    fun `should paginate schedules until page limit is reached`() {
        // given
        val schedules = mutableListOf<EventDTO>()
        queueActions(PagingConstants.PAGE_LIMIT) {
            mockWebServer.enqueueResponse(fileName = SCHEDULES_RESPONSE, code = 200)
        }
        assertTrue(remoteDataSource.currentPage == 0)
        assertTrue(remoteDataSource.lastId == 0)
        // when
        queueActions(PagingConstants.PAGE_LIMIT.plus(1)) {
            schedules.addAll(remoteDataSource.fetchSchedules(refresh = false))
        }
        // then
        assertTrue(remoteDataSource.currentPage == PagingConstants.PAGE_LIMIT)
        assertTrue(remoteDataSource.lastId == schedules.last().id?.toInt())
    }

    @Test
    fun `should return an empty list when page limit is reached`() {
        // given
        val schedules = mutableListOf<EventDTO>()
        queueActions(PagingConstants.PAGE_LIMIT.plus(1)) {
            mockWebServer.enqueueResponse(fileName = SCHEDULES_RESPONSE, code = 200)
        }
        // when
        for (i in 0 until PagingConstants.PAGE_LIMIT.plus(1)) {
            val result = remoteDataSource.fetchSchedules(refresh = false)
            if (i == PagingConstants.PAGE_LIMIT) {
                schedules.addAll(result)
            }
        }
        // then
        assertTrue(remoteDataSource.currentPage == PagingConstants.PAGE_LIMIT)
        assertTrue(schedules.isEmpty())
    }

    @Test
    fun `should restart pagination when refresh is true`() {
        // given
        queueActions(times = PagingConstants.PAGE_LIMIT) {
            mockWebServer.enqueueResponse(fileName = SCHEDULES_RESPONSE, code = 200)
        }
        // when
        queueActions(PagingConstants.PAGE_LIMIT) {
            remoteDataSource.fetchSchedules(refresh = false)
        }
        queueActions(times = 1) {
            mockWebServer.enqueueResponse(fileName = SCHEDULES_RESPONSE, code = 200)
        }
        val schedules = remoteDataSource.fetchSchedules(refresh = true)
        // then
        assertTrue(remoteDataSource.currentPage == 1)
        assertTrue(schedules.isNotEmpty())
    }

    @Test(expected = ServerException::class)
    fun `should throw a server exception on 404 response`() {
        // given
        mockWebServer.enqueueResponse(fileName = ERROR_RESPONSE, code = 404)
        // when
        remoteDataSource.fetchSchedules(refresh = false)
    }

    private companion object {
        const val SCHEDULES_RESPONSE = "schedules-success-response"
        const val ERROR_RESPONSE = "error-response"
    }
}