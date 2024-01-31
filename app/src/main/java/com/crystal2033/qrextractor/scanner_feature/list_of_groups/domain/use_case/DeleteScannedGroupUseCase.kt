package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteScannedGroupRepository
import kotlinx.coroutines.flow.Flow

class DeleteScannedGroupUseCase(
    private val repository: DeleteScannedGroupRepository
) {
    operator fun invoke(scannedGroup: ScannedGroup): Flow<Resource<Unit>> {
        return repository.deleteScannedGroupWithObjects(scannedGroup)
    }
}