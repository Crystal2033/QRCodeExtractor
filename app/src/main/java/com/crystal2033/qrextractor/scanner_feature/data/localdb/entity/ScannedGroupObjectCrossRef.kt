package com.crystal2033.qrextractor.scanner_feature.data.localdb.entity

import androidx.room.Entity

@Entity(primaryKeys = ["scannedObjectId", "scannedGroupId"])
data class ScannedGroupObjectCrossRef(
    val scannedObjectId: Long,
    val scannedGroupId: Long
)
