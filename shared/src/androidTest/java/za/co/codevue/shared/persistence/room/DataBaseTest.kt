package za.co.codevue.shared.persistence.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import java.io.IOException

internal abstract class DataBaseTest {
    lateinit var db: Database

    @Before
    fun createDb() {
        ApplicationProvider.getApplicationContext<Context>().also {
            db = Room.inMemoryDatabaseBuilder(it, Database::class.java)
                .allowMainThreadQueries()
                .build()
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }
}