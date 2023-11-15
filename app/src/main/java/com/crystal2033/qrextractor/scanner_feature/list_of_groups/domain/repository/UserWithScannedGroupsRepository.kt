package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups
import kotlinx.coroutines.flow.Flow

interface UserWithScannedGroupsRepository {
    fun getScannedGroupsForUserFromDb(
        userId: Long
    ) : Flow<Resource<UserWithScannedGroups>>
}