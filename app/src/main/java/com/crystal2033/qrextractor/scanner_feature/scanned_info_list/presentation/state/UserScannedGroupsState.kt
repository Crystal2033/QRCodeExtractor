package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.state

import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.UserWithScannedGroups

data class UserScannedGroupsState(
    val userScannedGroups: UserWithScannedGroups? = null,
    val isLoading: Boolean? = false
)
