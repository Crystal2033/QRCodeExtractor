package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteScannedGroupRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import kotlinx.coroutines.flow.Flow

class DeleteScannedGroupUseCase(
    private val repository: DeleteScannedGroupRepository
) {
    operator fun invoke(scannedGroup: ScannedGroupEntity): Flow<Resource<Unit>> {
        return repository.deleteScannedGroup(scannedGroup)
    }
}