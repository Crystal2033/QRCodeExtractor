package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case

import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import kotlinx.coroutines.flow.Flow

class InsertScannedGroupInDBUseCase(
    private val repository: ScannedGroupCreatorRepository
) {
    operator fun invoke(
        qrScannableDataList: List<QRScannableData>,
        user: User,
        groupName: String
    ): Flow<Resource<Unit>> {
        return repository.insertScannedGroupInDb(qrScannableDataList, user, groupName)
    }
}