package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChairAPI {

    @GET(value = RemoteServerConstants.CHAIR_URL)
    suspend fun getAllChairsInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<ChairDTO>>

    @GET(value = "${RemoteServerConstants.CHAIR_URL}/{chairId}")
    suspend fun getChairById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "chairId") chairId: Long,
    ): Response<ChairDTO>

    @POST(value = RemoteServerConstants.CHAIR_URL)
    suspend fun addChair(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body chairDTO: ChairDTO,
    ): Response<ChairDTO>

    @PUT(value = RemoteServerConstants.CHAIR_URL)
    suspend fun updateChair(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body chairDTO: ChairDTO,
    ): Response<ChairDTO>

    @DELETE(value = "${RemoteServerConstants.CHAIR_URL}/{chairId}")
    suspend fun deleteChair(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "chairId") chairId: Long,
    ): Response<Unit>

}