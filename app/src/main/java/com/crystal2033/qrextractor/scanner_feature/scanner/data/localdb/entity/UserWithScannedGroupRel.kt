package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.crystal2033.qrextractor.core.localdb.entity.UserEntity

data class UserWithScannedGroupRel(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )

    val scannedGroups: List<ScannedGroupEntity>
)
