package com.crystal2033.qrextractor.core.remote_server.api

import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectorAPI {
    @GET(value = RemoteServerConstants.PROJECTOR_URL)
    suspend fun getAllProjectorsInCabinet(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long
    ): Response<List<ProjectorDTO>>

    @GET(value = "${RemoteServerConstants.PROJECTOR_URL}/{projectorId}")
    suspend fun getProjectorById(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "projectorId") projectorId: Long,
    ): Response<ProjectorDTO>

    @POST(value = RemoteServerConstants.PROJECTOR_URL)
    suspend fun addProjector(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body projectorDTO: ProjectorDTO,
    ): Response<ProjectorDTO>

    @PUT(value = RemoteServerConstants.PROJECTOR_URL)
    suspend fun updateProjector(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Body projectorDTO: ProjectorDTO,
    ): Response<ProjectorDTO>

    @DELETE(value = "${RemoteServerConstants.PROJECTOR_URL}/{projectorId}")
    suspend fun deleteProjector(
        @Path(value = "orgId") orgId: Long,
        @Path(value = "branchId") branchId: Long,
        @Path(value = "buildingId") buildingId: Long,
        @Path(value = "cabinetId") cabinetId: Long,
        @Path(value = "projectorId") projectorId: Long,
    ): Response<Unit>
}