package com.crystal2033.qrextractor.core.localdb.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.ScannedGroupWithScannedObjects
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.UserWithScannedGroupsAndObjects
import kotlinx.coroutines.flow.Flow

interface ScannedObjectsListRepository {
    fun getUserScannedGroupsWithScannedObjects(userId: Int) : Flow<Resource<List<ScannedGroupWithScannedObjects>>>
}