package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person

import com.crystal2033.qrextractor.core.dto.DepartmentDto
import com.crystal2033.qrextractor.core.dto.TitleDto
import com.crystal2033.qrextractor.core.util.Resource
import retrofit2.Response
import retrofit2.http.GET

interface TitleApi {
    @GET(value = "api/titles")
    suspend fun getTitles() : Response<List<TitleDto>?>
}