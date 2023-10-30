package com.crystal2033.qrextractor.scanner_feature.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedObject(
    val tableNameInRemoteDb: String,
    val tableIdInRemoteDb: Long,

    @PrimaryKey(autoGenerate = true)
    val scannedObjectId: Long
)
