package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import kotlinx.coroutines.flow.Flow

interface DeleteScannedGroupRepository {
    fun deleteScannedGroupWithObjects(
        scannedGroup: ScannedGroup
    ): Flow<Resource<Unit>>
}