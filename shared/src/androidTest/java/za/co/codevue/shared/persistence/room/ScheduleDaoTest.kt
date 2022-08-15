package za.co.codevue.shared.persistence.room

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import za.co.codevue.shared.models.entities.ScheduleEntity

@RunWith(JUnit4::class)
@SmallTest
internal class ScheduleDaoTest : DataBaseTest() {
    private lateinit var scheduleDao: IScheduleDao

    @Before
    fun createDao() {
        super.createDb()
        scheduleDao = db.scheduleDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadEvents() = runBlocking {
        // given
        val mockSchedules = listOf(TEST_SCHEDULE)
        // when
        scheduleDao.saveSchedules(mockSchedules)
        val schedules = scheduleDao.getSchedulesTest()
        // then
        assertEquals(mockSchedules, schedules)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteEvents() = runBlocking {
        with(scheduleDao) {
            // when
            saveSchedules(listOf(TEST_SCHEDULE))
            deleteSchedules()
            val schedules = getSchedulesTest()
            // then
            assertTrue(schedules.isEmpty())
        }

    }

    internal companion object {
        private val TEST_SCHEDULE = ScheduleEntity(
            id = "id",
            date = "2022-08-15T01:14:10.071Z",
            imageUrl = "image.com",
            subtitle = "Rick and Morty Space League",
            title = "Rick vs Morty"
        )
    }
}