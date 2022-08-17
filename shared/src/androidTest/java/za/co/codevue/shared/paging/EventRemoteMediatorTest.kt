package za.co.codevue.shared.paging

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_EVENT_DTO
import mocks.MockEventRemoteDataSource
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import za.co.codevue.shared.data.datasource.LocalEventDataSourceImpl
import za.co.codevue.shared.models.entities.EventEntity
import za.co.codevue.shared.persistence.room.DataBaseTest

@RunWith(JUnit4::class)
@SmallTest
internal class EventRemoteMediatorTest : DataBaseTest() {
    private lateinit var mockRemoteDataSource: MockEventRemoteDataSource
    private lateinit var localDataSource: LocalEventDataSourceImpl

    @Before
    fun setup() {
        localDataSource = LocalEventDataSourceImpl(db.eventDao())
        mockRemoteDataSource = MockEventRemoteDataSource()
    }

    @Test
    fun shouldReturnSuccessWhenLoadStateIsRefreshAndMoreDataIsPresent() =
        runBlocking {
            // given
            mockRemoteDataSource.addEvent(DEFAULT_EVENT_DTO)
            val remoteMeditor = EventRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, EventEntity>(
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
            val remoteMeditor = EventRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, EventEntity>(
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
            val remoteMeditor = EventRemoteMediator(
                localDataSource,
                mockRemoteDataSource
            )
            val pagingState = PagingState<Int, EventEntity>(
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