package com.rulhouse.airpollution.model.remote.air_pollution.dto

data class AirPollutionInformation(
    val __extras: Extras,
    val _links: Links,
    val fields: List<Field>,
    val include_total: Boolean,
    val limit: String,
    val offset: String,
    val records: List<Record>,
    val resource_format: String,
    val resource_id: String,
    val total: String
)