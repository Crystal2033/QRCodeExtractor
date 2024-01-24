package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.OrganizationDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrganizationAPI {
    @GET(value = RemoteServerConstants.ORGANIZATION_URL)
    suspend fun getAllOrganization(): Response<List<OrganizationDTO>>

    @GET(value = "${RemoteServerConstants.ORGANIZATION_URL}/{orgId}")
    suspend fun getOrganizationById(@Path(value = "orgId") orgId: Long): Response<OrganizationDTO>
}