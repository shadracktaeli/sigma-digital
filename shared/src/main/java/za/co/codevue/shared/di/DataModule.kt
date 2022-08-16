package za.co.codevue.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.codevue.shared.data.datasource.LocalEventDataSourceImpl
import za.co.codevue.shared.data.datasource.RemoteDataSourceImpl
import za.co.codevue.shared.data.repository.EventRepositoryImpl
import za.co.codevue.shared.domain.datasource.ILocalEventDataSource
import za.co.codevue.shared.domain.datasource.IRemoteDataSource
import za.co.codevue.shared.domain.repository.IEventRepository
import za.co.codevue.shared.network.retrofit.service.IEventApiService
import za.co.codevue.shared.persistence.room.IEventDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    @Singleton
    fun provideLocalEventDataSource(dao: IEventDao): ILocalEventDataSource {
        return LocalEventDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideRemoteEventDataSource(api: IEventApiService): IRemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        localDataSource: ILocalEventDataSource,
        remoteDataSource: IRemoteDataSource
    ): IEventRepository {
        return EventRepositoryImpl(localDataSource, remoteDataSource)
    }
}