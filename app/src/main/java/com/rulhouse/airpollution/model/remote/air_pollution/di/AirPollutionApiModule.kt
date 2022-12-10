package com.rulhouse.airpollution.model.remote.air_pollution.di

import com.rulhouse.airpollution.model.remote.air_pollution.impl.AirPollutionApiImpl
import com.rulhouse.airpollution.model.remote.air_pollution.repository.AirPollutionApiRepository
import com.rulhouse.airpollution.model.remote.air_pollution.service.AirPollutionApiService
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.AirPollutionApiUseCases
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.GetRecords
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirPollutionApiModule {
    private const val BASE_URL = "https://data.epa.gov.tw/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideRedditApiService(retrofit: Retrofit): AirPollutionApiService {
        return retrofit.create(AirPollutionApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(apiService: AirPollutionApiService): AirPollutionApiRepository {
        return AirPollutionApiImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideRedditApiUseCases(repository: AirPollutionApiRepository): AirPollutionApiUseCases {
        return AirPollutionApiUseCases(
            getRecords = GetRecords(repository)
        )
    }
}