package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteObjectItemInScannedGroupRepository
import kotlinx.coroutines.flow.Flow

class DeleteObjectItemInScannedGroupUseCase(
    private val repository: DeleteObjectItemInScannedGroupRepository
) {
    operator fun invoke(scannedGroupId: Long, scannedObjectId: Long): Flow<Resource<Unit>> {
        return repository.deleteObjectItemInScannedGroup(scannedGroupId, scannedObjectId)
    }
}