package com.crystal2033.qrextractor.scanner_feature.data.remote.api

import com.crystal2033.qrextractor.scanner_feature.data.remote.dto.PersonDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {
    @GET("api/persons/{id}")
    suspend fun getPerson(@Path(value = "id") id: Int) : PersonDto?
}