package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case

import com.crystal2033.qrextractor.core.localdb.repository.ScannedObjectsListRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupWithScannedObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetScannedGroupsWithObjectsUseCase(
    private val repository : ScannedObjectsListRepository
) {
    operator fun invoke(id: Int): Flow<Resource<List<ScannedGroupWithScannedObjects>>> {
        if (id < 1) {
            return flow { }
        }

        return repository.getUserScannedGroupsWithScannedObjects(id)
    }

}