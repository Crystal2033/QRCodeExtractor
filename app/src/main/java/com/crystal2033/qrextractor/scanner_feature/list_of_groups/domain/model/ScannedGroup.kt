package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId

data class ScannedGroup(
    val id: Long? = null,
    val groupName: String? = null,
    val listOfScannedObjects: MutableList<Pair<ScannedTableNameAndId, Long>> = mutableListOf()
) {
}