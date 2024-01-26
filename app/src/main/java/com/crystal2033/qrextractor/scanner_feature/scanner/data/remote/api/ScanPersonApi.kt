//package com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.api
//
//import com.crystal2033.qrextractor.core.dto.PersonDto
//import retrofit2.Response
//import retrofit2.http.GET
//import retrofit2.http.Path
//
//interface ScanPersonApi {
//    @GET("api/persons/{id}")
//    suspend fun getPerson(@Path(value = "id") id: Long) : Response<PersonDto?>
//}