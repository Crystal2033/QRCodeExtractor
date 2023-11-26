package com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.api

import com.crystal2033.qrextractor.core.dto.KeyboardDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ScanKeyboardApi {

    @GET(value = "api/keyboards/{id}")
    suspend fun getKeyboard(@Path(value = "id") id: Long) : Response<KeyboardDto?>
}