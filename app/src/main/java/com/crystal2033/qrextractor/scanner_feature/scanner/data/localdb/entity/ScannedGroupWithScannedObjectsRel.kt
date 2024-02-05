package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedObjectWithIdInLocalDB

data class ScannedGroupWithScannedObjectsRel(
    @Embedded val scannedGroup: ScannedGroupEntity,
    @Relation(
        parentColumn = "scannedGroupId",
        entityColumn = "scannedObjectId",
        associateBy = Junction(ScannedGroupObjectCrossRef::class)
    )
    val scannedObjects: List<ScannedObjectEntity>
) {
    fun toScannedGroupWithObjects(): ScannedGroup {
        return ScannedGroup(
            id = scannedGroup.scannedGroupId,
            groupName = scannedGroup.groupName,
            listOfScannedObjects = scannedObjects.map { scannedObjectEntity ->
                ScannedObjectWithIdInLocalDB(
                    scannedObjectEntity.toScannedTableNameAndId(),
                    scannedObjectEntity.scannedObjectId
                )
            }.toMutableList()
        )
    }
}
