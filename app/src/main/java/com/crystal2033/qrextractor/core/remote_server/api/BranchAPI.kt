package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.BranchDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BranchAPI {
    @GET(value = RemoteServerConstants.BRANCH_URL)
    suspend fun getAllBranchesByOrg(@Path(value = "orgId") orgId: Long) : Response<List<BranchDTO>>
}