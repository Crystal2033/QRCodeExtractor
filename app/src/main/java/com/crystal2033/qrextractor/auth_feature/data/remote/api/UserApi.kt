package com.crystal2033.qrextractor.auth_feature.data.remote.api

import com.crystal2033.qrextractor.auth_feature.data.dto.UserDTO
import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST(value = "api/users/auth")
    suspend fun tryLogin(@Body userLoginDTO: UserLoginDTO): Response<UserDTO?>
}