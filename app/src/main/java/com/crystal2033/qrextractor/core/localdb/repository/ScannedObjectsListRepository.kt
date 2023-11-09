package com.crystal2033.qrextractor.core.localdb.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupWithScannedObjectsRel
import kotlinx.coroutines.flow.Flow

interface ScannedObjectsListRepository {
    fun getUserScannedGroupsWithScannedObjects(userId: Int) : Flow<Resource<List<ScannedGroupWithScannedObjectsRel>>>
}