package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.crystal2033.qrextractor.core.localdb.entity.UserEntity

data class UserWithScannedGroupsAndObjects(
    @Embedded val user: UserEntity,
    @Relation(
        entity = ScannedGroupEntity::class,
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )

    val scannedGroupWithScannedObjects: List<ScannedGroupWithScannedObjects>
)
