package com.crystal2033.qrextractor.scanner_feature.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.domain.repository.PersonRepository
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.GetDataFromQRCodeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow

class GetPersonFromQRCodeUseCase(
    private val repository: PersonRepository
) : GetDataFromQRCodeUseCase {
    override operator fun invoke(id: Int): Flow<Resource<QRScannableData>> {
        if (id < 1) {
            return flow { }
        }

        return repository.getPerson(id).filterIsInstance()
    }
}
