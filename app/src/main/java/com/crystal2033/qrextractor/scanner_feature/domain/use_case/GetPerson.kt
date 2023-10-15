package com.crystal2033.qrextractor.scanner_feature.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPerson(
    private val repository: PersonRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Person>>{
        if (id < 1){
            return flow { }
        }
        return repository.getPerson(id)
    }
}