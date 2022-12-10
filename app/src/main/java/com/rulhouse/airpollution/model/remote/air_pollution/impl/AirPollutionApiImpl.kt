package com.rulhouse.airpollution.model.remote.air_pollution.impl

import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record
import com.rulhouse.airpollution.model.remote.air_pollution.repository.AirPollutionApiRepository
import com.rulhouse.airpollution.model.remote.air_pollution.service.AirPollutionApiService
import com.rulhouse.airpollution.model.remote.response.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AirPollutionApiImpl(
    private val apiService: AirPollutionApiService
): AirPollutionApiRepository {

    override suspend fun getRecords(): Flow<BaseResult<List<Record>, Int>> {
        val response = apiService.getPollutionInformation(
            limit = 1000,
            apiKey = "cebebe84-e17d-4022-a28f-81097fda5896",
            sort = "ImportDate desc",
            format = "json"
        )
        return flow {
            if (response.isSuccessful) {
                if (response.body() == null) {
                    emit(BaseResult.Success(emptyList()))
                    return@flow
                }
                val records = response.body()!!.records
                emit(BaseResult.Success(records))
            } else {
                val code = response.code()
                emit(BaseResult.Error(code))
            }
        }
    }

}