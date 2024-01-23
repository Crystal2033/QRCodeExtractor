package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.KeyboardDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KeyboardAPI {
    @GET(value = RemoteServerConstants.KEYBOARD_URL)
    suspend fun getAllKeyboardsInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<KeyboardDTO>>

    @GET(value = "${RemoteServerConstants.KEYBOARD_URL}/{keyboardId}")
    suspend fun getKeyboardById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "keyboardId") keyboardId: Long,
    ): Response<KeyboardDTO>

    @POST(value = RemoteServerConstants.KEYBOARD_URL)
    suspend fun addKeyboard(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body keyboardDTO: KeyboardDTO,
    ): Response<KeyboardDTO>

    @PUT(value = RemoteServerConstants.KEYBOARD_URL)
    suspend fun updateKeyboard(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body keyboardDTO: KeyboardDTO,
    ): Response<KeyboardDTO>

    @DELETE(value = "${RemoteServerConstants.KEYBOARD_URL}/{keyboardId}")
    suspend fun deleteKeyboard(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "keyboardId") keyboardId: Long,
    ): Response<Unit>
}