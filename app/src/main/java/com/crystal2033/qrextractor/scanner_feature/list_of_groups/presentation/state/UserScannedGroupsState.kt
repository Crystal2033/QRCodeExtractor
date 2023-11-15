package com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.state

import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups

data class UserScannedGroupsState(
    val userScannedGroups: UserWithScannedGroups? = null,
    val isLoading: Boolean = false
)
