package za.co.codevue.shared.paging

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_SCHEDULE_DTO
import mocks.MockScheduleRemoteDataSource
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import za.co.codevue.shared.data.datasource.LocalScheduleDataSourceImpl
import za.co.codevue.shared.models.entities.ScheduleEntity
import za.co.codevue.shared.persistence.room.DataBaseTest

@RunWith(JUnit4::class)
@SmallTest
internal class ScheduleRemoteMediatorTest : DataBaseTest() {
    private lateinit var mockRemoteDataSource: MockScheduleRemoteDataSource
    private lateinit var localDataSource: LocalScheduleDataSourceImpl

    @Before
    fun setup() {
        localDataSource = LocalScheduleDataSourceImpl(db.scheduleDao())
        mockRemoteDataSource = MockScheduleRemoteDataSource()
    }

    @Test
    fun shouldReturnSuccessWhenLoadStateIsRefreshAndMoreDataIsPresent() =
        runBlocking {
            // given
            mockRemoteDataSource.addSchedule(DEFAULT_SCHEDULE_DTO)
            val remoteMeditor = ScheduleRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, ScheduleEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 10
            )
            // when
            val result = remoteMeditor.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun shouldReturnSuccessAndEndOfPaginationWhenNoMoreData() =
        runBlocking {
            // given
            val remoteMeditor = ScheduleRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, ScheduleEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 10
            )
            // when
            val result = remoteMeditor.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun shouldReturnErrorWhenErrorOccurs() =
        runBlocking {
            // given
            mockRemoteDataSource.throwError = true
            val remoteMeditor = ScheduleRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, ScheduleEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(10),
                leadingPlaceholderCount = 10
            )
            // when
            val result = remoteMeditor.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Error)
        }
}