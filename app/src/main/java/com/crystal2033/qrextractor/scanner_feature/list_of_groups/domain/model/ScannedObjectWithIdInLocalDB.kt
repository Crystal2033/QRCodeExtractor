package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId

//id in local db
data class ScannedObjectWithIdInLocalDB(
    val scannedObjectInfo: ScannedTableNameAndId,
    val idInLocalDB: Long
) {
}