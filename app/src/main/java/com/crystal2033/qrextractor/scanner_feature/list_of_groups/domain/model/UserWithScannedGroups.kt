package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model

import com.crystal2033.qrextractor.core.model.User

data class UserWithScannedGroups(
    val user: User? = null,
    val scannedGroups: List<ScannedGroup>? = null
){
    fun getScannedGroupById(id: Long): ScannedGroup?{
        return scannedGroups?.find { group ->
            group.id == id
        }
    }
}
