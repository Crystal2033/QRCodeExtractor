package com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetDataFromQRCodeUseCase {
    operator fun invoke(id: Int): Flow<Resource<Person>>
}