package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SystemUnitAPI {
    @GET(value = RemoteServerConstants.SYSTEM_UNIT_URL)
    suspend fun getAllSystemUnitsInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<SystemUnitDTO>>

    @GET(value = "${RemoteServerConstants.SYSTEM_UNIT_URL}/{systemUnitId}")
    suspend fun getSystemUnitById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "systemUnitId") systemUnitId: Long,
    ): Response<SystemUnitDTO>

    @POST(value = RemoteServerConstants.SYSTEM_UNIT_URL)
    suspend fun addSystemUnit(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body systemUnitDTO: SystemUnitDTO,
    ): Response<SystemUnitDTO>

    @PUT(value = RemoteServerConstants.SYSTEM_UNIT_URL)
    suspend fun updateSystemUnit(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body systemUnitDTO: SystemUnitDTO,
    ): Response<SystemUnitDTO>

    @DELETE(value = "${RemoteServerConstants.SYSTEM_UNIT_URL}/{systemUnitId}")
    suspend fun deleteSystemUnit(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "systemUnitId") systemUnitId: Long,
    ): Response<Unit>
}