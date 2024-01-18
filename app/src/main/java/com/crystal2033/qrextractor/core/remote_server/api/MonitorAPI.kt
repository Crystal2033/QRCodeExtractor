package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MonitorAPI {
    @GET(value = RemoteServerConstants.MONITOR_UNIT_URL)
    suspend fun getAllMonitorsInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<MonitorDTO>>

    @GET(value = "${RemoteServerConstants.MONITOR_UNIT_URL}/{monitorId}")
    suspend fun getMonitorById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "monitorId") monitorId: Long,
    ): Response<MonitorDTO>

    @POST(value = RemoteServerConstants.MONITOR_UNIT_URL)
    suspend fun addMonitor(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body monitorDTO: MonitorDTO,
    ): Response<MonitorDTO>

    @PUT(value = RemoteServerConstants.MONITOR_UNIT_URL)
    suspend fun updateMonitor(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body monitorDTO: MonitorDTO,
    ): Response<MonitorDTO>

    @DELETE(value = "${RemoteServerConstants.MONITOR_UNIT_URL}/{monitorId}")
    suspend fun deleteMonitor(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "monitorId") monitorId: Long,
    ): Response<Unit>
}