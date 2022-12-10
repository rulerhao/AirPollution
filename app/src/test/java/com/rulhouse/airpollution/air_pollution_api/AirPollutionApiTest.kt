package com.rulhouse.airpollution.air_pollution_api

import com.rulhouse.airpollution.model.remote.air_pollution.impl.AirPollutionApiImpl
import com.rulhouse.airpollution.model.remote.air_pollution.service.AirPollutionApiService
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.AirPollutionApiUseCases
import com.rulhouse.airpollution.model.remote.air_pollution.use_cases.GetRecords
import com.rulhouse.airpollution.model.remote.response.BaseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirPollutionApiTest {
    private val BASE_URL = "https://data.epa.gov.tw/"

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    private val apiService = retrofit.create(AirPollutionApiService::class.java)

    private val repository = AirPollutionApiImpl(apiService)

    private val airPollutionApiUseCases = AirPollutionApiUseCases(
        getRecords = GetRecords(repository)
    )

    private val mockItems = AirPollutionApiMockItems()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSiteId1() = runTest {
        airPollutionApiUseCases.getRecords().collectLatest { baseResult ->
            when (baseResult) {
                is BaseResult.Success -> {
                    assertEquals(mockItems.siteNameOfSiteId1Expected, baseResult.data[0].sitename)
                }
                is BaseResult.Error -> {}
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSiteId313() = runTest {
        airPollutionApiUseCases.getRecords().collectLatest { baseResult ->
            when (baseResult) {
                is BaseResult.Success -> {
                    assertEquals(mockItems.siteNameOfSiteId2Expected, baseResult.data[1].sitename)
                }
                is BaseResult.Error -> {}
            }
        }
    }

}