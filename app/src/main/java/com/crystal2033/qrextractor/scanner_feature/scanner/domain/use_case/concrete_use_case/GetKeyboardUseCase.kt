package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetScannableDataFromServerUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow

class GetKeyboardUseCase(
    private val repository: KeyboardRepository
) : GetScannableDataFromServerUseCase {
    override operator fun invoke(id: Long): Flow<Resource<QRScannableData>> {
        if (id < 1) {
            return flow { }
        }

        return repository.getKeyboard(id).filterIsInstance()
    }

}