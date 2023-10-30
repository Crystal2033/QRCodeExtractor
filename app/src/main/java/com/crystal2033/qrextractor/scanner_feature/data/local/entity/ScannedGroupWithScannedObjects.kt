package com.crystal2033.qrextractor.scanner_feature.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ScannedGroupWithScannedObjects(
    @Embedded val scannedGroup: ScannedGroup,
    @Relation(
        parentColumn = "scannerGroupId",
        entityColumn = "scannedObjectId",
        associateBy = Junction(ScannedGroupObjectCrossRef::class)
    )

    val scannedObjects: List<ScannedObject>
)
