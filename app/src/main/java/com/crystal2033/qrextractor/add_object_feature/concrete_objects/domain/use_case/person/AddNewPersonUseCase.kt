package com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.AddPersonRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.AddOnRemoteServerUseCase
import com.crystal2033.qrextractor.core.model.Person
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class AddNewPersonUseCase(
    private val addPersonRepository: AddPersonRepository
) : AddOnRemoteServerUseCase<Person> {
    override fun invoke(newObject: Person): Flow<Resource<Person>> {
        return addPersonRepository.addPerson(newObject.toPersonDto())
    }

}