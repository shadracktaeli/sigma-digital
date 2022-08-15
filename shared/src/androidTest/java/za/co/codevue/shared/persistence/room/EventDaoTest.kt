package za.co.codevue.shared.persistence.room

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import za.co.codevue.shared.models.entities.EventEntity

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
        val mockEvents = listOf(TEST_EVENT)
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
            saveEvents(listOf(TEST_EVENT))
            deleteEvents()
            val events = getEventsTest()
            // then
            assertTrue(events.isEmpty())
        }

    }

    internal companion object {
        private val TEST_EVENT = EventEntity(
            id = "id",
            date = "2022-08-15T01:14:10.071Z",
            imageUrl = "image.com",
            subtitle = "Rick and Morty Space League",
            title = "Rick vs Morty",
            videoUrl = "video.com"
        )
    }
}