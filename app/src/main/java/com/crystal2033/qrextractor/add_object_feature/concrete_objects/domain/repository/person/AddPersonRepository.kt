package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person

import com.crystal2033.qrextractor.core.dto.PersonDto
import com.crystal2033.qrextractor.core.model.Person
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AddPersonRepository {
    fun addPerson(person: PersonDto): Flow<Resource<Person>>
}