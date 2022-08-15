package za.co.codevue.shared.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import za.co.codevue.shared.BuildConfig
import za.co.codevue.shared.persistence.room.Database
import za.co.codevue.shared.persistence.room.DatabaseConstants
import za.co.codevue.shared.persistence.room.IEventDao
import za.co.codevue.shared.persistence.room.IScheduleDao
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, DatabaseConstants.NAME)
            .allowMainThreadQueries()
            .setQueryCallback({ sqlQuery, bindArgs ->
                if (BuildConfig.DEBUG) {
                    // log sql statements
                    Timber.tag("Room").d("SQL Query: $sqlQuery Args: $bindArgs")
                }
            }, Executors.newSingleThreadExecutor())
            .build()
    }

    @Provides
    @Singleton
    fun provideEventDao(database: Database): IEventDao {
        return database.eventDao()
    }

    @Provides
    @Singleton
    fun provideScheduleDao(database: Database): IScheduleDao {
        return database.scheduleDao()
    }
}