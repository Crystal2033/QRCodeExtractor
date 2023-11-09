package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model

import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.ScannedTableNameAndId

data class ScannedGroup(
    val groupName: String? = null,
    val listOfScannedObjects: List<ScannedTableNameAndId>? = null
) {
}