package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ScannedGroupWithScannedObjects(
    @Embedded val scannedGroup: ScannedGroupEntity,
    @Relation(
        parentColumn = "scannedGroupId",
        entityColumn = "scannedObjectId",
        associateBy = Junction(ScannedGroupObjectCrossRef::class)
    )

    val scannedObjects: List<ScannedObjectEntity>
)
