package za.co.codevue.shared.persistence.room

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_EVENT_ENTITY
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
internal class EventDaoTest : DataBaseTest() {
    private lateinit var eventDao: IEventDao

    @Before
    fun createDao() {
        super.createDb()
        eventDao = db.eventDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadEvents() = runBlocking {
        // given
        val mockEvents = listOf(DEFAULT_EVENT_ENTITY)
        // when
        eventDao.saveEvents(mockEvents)
        val events = eventDao.getEventsTest()
        // then
        assertEquals(mockEvents, events)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteEvents() = runBlocking {
        with(eventDao) {
            // when
            saveEvents(listOf(DEFAULT_EVENT_ENTITY))
            deleteEvents()
            val events = getEventsTest()
            // then
            assertTrue(events.isEmpty())
        }

    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadEventById() = runBlocking {
        with(eventDao) {
            // when
            saveEvents(listOf(DEFAULT_EVENT_ENTITY))
            val event = getEvent(DEFAULT_EVENT_ENTITY.id)
            // then
            assertEquals(DEFAULT_EVENT_ENTITY, event)
        }
    }
}