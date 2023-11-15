package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.UserWithScannedGroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetListOfUserScannedGroupsUseCase(private val repository : UserWithScannedGroupsRepository) {
    operator fun invoke(userId: Long): Flow<Resource<UserWithScannedGroups>>  {
        if (userId < 1){
            return flow{}
        }
        return repository.getScannedGroupsForUserFromDb(userId)
    }
}