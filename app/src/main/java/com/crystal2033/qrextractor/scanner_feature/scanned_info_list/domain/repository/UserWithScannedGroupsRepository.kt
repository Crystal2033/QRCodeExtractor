package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.UserWithScannedGroupsAndObjectsRel
import kotlinx.coroutines.flow.Flow

interface UserWithScannedGroupsRepository {
    fun getScannedGroupsForUserFromDb(
        userId: Long
    ) : Flow<Resource<UserWithScannedGroups>>
}