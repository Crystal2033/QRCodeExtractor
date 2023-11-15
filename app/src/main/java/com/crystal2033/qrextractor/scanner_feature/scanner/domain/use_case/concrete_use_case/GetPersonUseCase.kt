package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.PersonRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetDataFromServerUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow

class GetPersonUseCase(
    private val repository: PersonRepository
) : GetDataFromServerUseCase {
    override operator fun invoke(id: Long): Flow<Resource<QRScannableData>> {
        if (id < 1) {
            return flow { }
        }

        return repository.getPerson(id).filterIsInstance()
    }
}
