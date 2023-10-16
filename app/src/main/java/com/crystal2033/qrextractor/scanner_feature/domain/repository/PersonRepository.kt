package com.crystal2033.qrextractor.scanner_feature.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPerson(id: Int): Flow<Resource<Person>>
}