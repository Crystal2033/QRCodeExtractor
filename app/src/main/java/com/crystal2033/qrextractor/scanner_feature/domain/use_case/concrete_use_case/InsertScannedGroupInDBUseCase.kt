package com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case

import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.domain.repository.ScannedGroupRepository
import kotlinx.coroutines.flow.Flow

class InsertScannedGroupInDBUseCase(
    private val repository: ScannedGroupRepository
) {
    operator fun invoke(
        qrScannableDataList: List<QRScannableData>,
        user: User,
        groupName: String
    ): Flow<Resource<Unit>> {
        return repository.insertScannedGroupInDb(qrScannableDataList, user, groupName)
    }
}