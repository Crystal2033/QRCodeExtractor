package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.crystal2033.qrextractor.core.localdb.entity.UserEntity
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.UserWithScannedGroups

data class UserWithScannedGroupsAndObjectsRel(
    @Embedded val user: UserEntity,
    @Relation(
        entity = ScannedGroupEntity::class,
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )

    val scannedGroupWithScannedObjects: List<ScannedGroupWithScannedObjectsRel>
) {
    fun toUserWithScannedGroups(): UserWithScannedGroups {
        return UserWithScannedGroups(
            user = user.toUser(),
            scannedGroups = scannedGroupWithScannedObjects.map { scannedGroupWithObjects ->
                scannedGroupWithObjects.toScannedGroupWithObjects()
            }
        )
    }
}
