package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.RemoteServerConstants
import com.crystal2033.qrextractor.core.remote_server.data.dto.BuildingDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BuildingAPI {
    @GET(value = RemoteServerConstants.BUILDING_URL)
    suspend fun getAllBuildingsByOrgAndBranch(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long
    ): Response<List<BuildingDTO>>
}