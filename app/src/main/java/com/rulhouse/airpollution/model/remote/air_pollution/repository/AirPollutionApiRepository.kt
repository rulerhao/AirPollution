package com.rulhouse.airpollution.model.remote.air_pollution.repository

import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record
import com.rulhouse.airpollution.model.remote.response.BaseResult
import kotlinx.coroutines.flow.Flow

interface AirPollutionApiRepository {

    suspend fun getRecords(): Flow<BaseResult<List<Record>, Int>>

}