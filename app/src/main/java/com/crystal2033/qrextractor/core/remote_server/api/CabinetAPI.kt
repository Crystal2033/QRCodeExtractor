package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.RemoteServerConstants
import com.crystal2033.qrextractor.core.remote_server.data.dto.CabinetDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CabinetAPI {
    @GET(value = RemoteServerConstants.CABINET_URL)
    suspend fun getAllCabinetsByOrgBranchAndBuilding(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long
    ): Response<List<CabinetDTO>>
}