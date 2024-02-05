package com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.DeviceInfoInQRCodeRepresenter
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndID

//id in local db
data class ScannedObjectWithIdInLocalDB(
    val scannedObjectInfo: ScannedTableNameAndID,
    val idInLocalDB: Long
) {
}