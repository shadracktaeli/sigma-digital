package za.co.codevue.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import za.co.codevue.shared.BuildConfig
import za.co.codevue.shared.network.retrofit.ApiConstants
import za.co.codevue.shared.network.retrofit.service.IEventApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // Add request and response logging
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor { message ->
                    Timber.tag(ApiConstants.OKHTTP_LOG_TAG).d(message)
                }
                logger.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logger)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okhHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okhHttpClient)
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): IEventApiService {
        return retrofit.create(IEventApiService::class.java)
    }
}