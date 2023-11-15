package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import kotlinx.coroutines.flow.Flow

interface GetDataFromServerUseCase {
    operator fun invoke(id: Long): Flow<Resource<QRScannableData>>
}