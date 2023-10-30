package com.crystal2033.qrextractor.scanner_feature.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedObject(
    val tableNameInRemoteDb: String,
    val tableIdInRemoteDb: Long,

    @PrimaryKey(autoGenerate = true)
    var scannedObjectId: Long= 0
)
