package za.co.codevue.shared.persistence.room

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import mocks.DEFAULT_SCHEDULE_ENTITY
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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
    fun writeAndReadSchedules() = runBlocking {
        // given
        val mockSchedules = listOf(DEFAULT_SCHEDULE_ENTITY)
        // when
        scheduleDao.saveSchedules(mockSchedules)
        val schedules = scheduleDao.getSchedulesTest()
        // then
        assertEquals(mockSchedules, schedules)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteSchedules() = runBlocking {
        with(scheduleDao) {
            // when
            saveSchedules(listOf(DEFAULT_SCHEDULE_ENTITY))
            deleteSchedules()
            val schedules = getSchedulesTest()
            // then
            assertTrue(schedules.isEmpty())
        }
    }
}