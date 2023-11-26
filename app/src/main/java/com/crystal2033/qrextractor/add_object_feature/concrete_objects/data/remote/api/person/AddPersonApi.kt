package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person

import com.crystal2033.qrextractor.core.dto.PersonDto
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST

interface AddPersonApi {
    @POST("api/persons")
    suspend fun addPerson(personDto: PersonDto) : Flow<Resource<PersonDto>>
}