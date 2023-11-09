package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model

import com.crystal2033.qrextractor.core.User

data class UserWithScannedGroups(
    val user: User? = null,
    val scannedGroups: List<ScannedGroup>? = null
)
