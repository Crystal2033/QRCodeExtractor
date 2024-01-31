package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import kotlinx.coroutines.flow.Flow

interface DeleteScannedGroupRepository {
    fun deleteScannedGroup(
        scannedGroup: ScannedGroupEntity
    ): Flow<Resource<Unit>>
}