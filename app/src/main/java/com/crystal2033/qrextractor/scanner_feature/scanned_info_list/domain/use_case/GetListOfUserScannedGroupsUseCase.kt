package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case

import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.repository.UserWithScannedGroupsRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.UserWithScannedGroupsAndObjectsRel
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