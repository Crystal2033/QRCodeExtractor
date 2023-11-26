package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person

import com.crystal2033.qrextractor.core.dto.TitleDto
import com.crystal2033.qrextractor.core.dto.WorkSpaceDto
import com.crystal2033.qrextractor.core.util.Resource
import retrofit2.Response
import retrofit2.http.GET

interface WorkspaceApi {
    @GET(value = "api/workspaces")
    suspend fun getWorkspaces() : Response<List<WorkSpaceDto>?>
}