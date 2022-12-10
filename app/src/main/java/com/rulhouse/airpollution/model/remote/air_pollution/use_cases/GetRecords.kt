package com.rulhouse.airpollution.model.remote.air_pollution.use_cases

import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record
import com.rulhouse.airpollution.model.remote.air_pollution.repository.AirPollutionApiRepository
import com.rulhouse.airpollution.model.remote.response.BaseResult
import kotlinx.coroutines.flow.Flow

class GetRecords (
    private val repository: AirPollutionApiRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<Record>, Int>> {
        return repository.getRecords()
    }
}