package com.crystal2033.qrextractor.scanner_feature.data.remote.api

import com.crystal2033.qrextractor.scanner_feature.data.remote.dto.KeyboardDto
import retrofit2.http.GET
import retrofit2.http.Path

interface KeyboardApi {

    @GET(value = "api/keyboards/{id}")
    suspend fun getKeyboard(@Path(value = "id") id: Int) : KeyboardDto?
}