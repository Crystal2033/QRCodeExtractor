package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository

import com.crystal2033.qrextractor.core.localdb.ScannedObjectDao
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteObjectItemInScannedGroupRepository {
    fun deleteObjectItemInScannedGroup(
        scannedGroupId: Long, scannedObjectId: Long
    ): Flow<Resource<Unit>>
}