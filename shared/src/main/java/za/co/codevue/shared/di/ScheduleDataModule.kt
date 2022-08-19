package za.co.codevue.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.codevue.shared.data.datasource.LocalScheduleDataSourceImpl
import za.co.codevue.shared.data.datasource.RemoteScheduleDataSourceImpl
import za.co.codevue.shared.data.repository.ScheduleRepositoryImpl
import za.co.codevue.shared.domain.datasource.ILocalScheduleDataSource
import za.co.codevue.shared.domain.datasource.IRemoteScheduleDataSource
import za.co.codevue.shared.domain.repository.IScheduleRepository
import za.co.codevue.shared.network.retrofit.service.IEventApiService
import za.co.codevue.shared.persistence.room.IScheduleDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ScheduleDataModule {
    @Provides
    @Singleton
    fun provideLocalScheduleDataSource(dao: IScheduleDao): ILocalScheduleDataSource {
        return LocalScheduleDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideRemoteScheduleDataSource(api: IEventApiService): IRemoteScheduleDataSource {
        return RemoteScheduleDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(
        localDataSource: ILocalScheduleDataSource,
        remoteDataSource: IRemoteScheduleDataSource
    ): IScheduleRepository {
        return ScheduleRepositoryImpl(localDataSource, remoteDataSource)
    }
}