//package com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person
//
//import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.AddPersonApi
//import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.AddPersonRepository
//import com.crystal2033.qrextractor.core.dto.PersonDto
//import com.crystal2033.qrextractor.core.model.Person
//import com.crystal2033.qrextractor.core.util.Resource
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
//class AddPersonRepositoryImpl(
//    private val addPersonApi: AddPersonApi
//) : AddPersonRepository {
//    override fun addPerson(person: PersonDto): Flow<Resource<Person>> = flow {
//        emit(Resource.Loading())
//
//        val personDto = addPersonApi.addPerson(person = person).body()
//        //TODO: add errors catch
//        emit(Resource.Success(personDto?.toPerson()))
//    }
//}