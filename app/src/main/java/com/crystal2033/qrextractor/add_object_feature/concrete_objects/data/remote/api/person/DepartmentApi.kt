package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person

import com.crystal2033.qrextractor.core.dto.DepartmentDto
import com.crystal2033.qrextractor.core.util.Resource
import retrofit2.http.GET

interface DepartmentApi {
    @GET(value = "api/departments")
    suspend fun getDepartments() : Resource<List<DepartmentDto?>>
}