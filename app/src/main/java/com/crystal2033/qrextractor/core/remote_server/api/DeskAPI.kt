package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeskAPI {
    @GET(value = RemoteServerConstants.DESK_URL)
    suspend fun getAllDesksInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<DeskDTO>>

    @GET(value = "${RemoteServerConstants.DESK_URL}/{deskId}")
    suspend fun getDeskById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "deskId") deskId: Long,
    ): Response<DeskDTO>

    @POST(value = RemoteServerConstants.DESK_URL)
    suspend fun addDesk(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body deskDTO: DeskDTO,
    ): Response<DeskDTO>

    @PUT(value = RemoteServerConstants.DESK_URL)
    suspend fun updateDesk(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body deskDTO: DeskDTO,
    ): Response<DeskDTO>

    @DELETE(value = "${RemoteServerConstants.DESK_URL}/{deskId}")
    suspend fun deleteDesk(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "deskId") deskId: Long,
    ): Response<Unit>
}