package com.rulhouse.airpollution.model.remote.air_pollution.service

import com.rulhouse.airpollution.model.remote.air_pollution.dto.AirPollutionInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirPollutionApiService {

    @GET("api/v2/aqx_p_432")
    suspend fun getPollutionInformation(
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String,
        @Query("sort") sort: String,
        @Query("format") format: String
    ): Response<AirPollutionInformation>

}