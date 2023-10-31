package com.crystal2033.qrextractor.scanner_feature.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithScannedGroup(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )

    val scannedGroups: List<ScannedGroupEntity>
)
