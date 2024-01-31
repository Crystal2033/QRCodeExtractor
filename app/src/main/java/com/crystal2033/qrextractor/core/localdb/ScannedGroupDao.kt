package com.crystal2033.qrextractor.core.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupObjectCrossRef
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupWithScannedObjectsRel

@Dao
interface ScannedGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScannedGroup(scannedGroup: ScannedGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScannedGroupCrossRef(scannedGroupObjectCrossRef: ScannedGroupObjectCrossRef): Long

    @Query("DELETE FROM ScannedGroup WHERE scannedGroupId=:scannedGroupId")
    suspend fun deleteScannedGroup(scannedGroupId: Long)


    @Query("SELECT * FROM ScannedGroup WHERE scannedGroupId=:scannedGroupId")
    suspend fun getScannedGroupWithItsObjects(scannedGroupId: Long): ScannedGroupWithScannedObjectsRel?

    @Query(
        "DELETE FROM ScannedGroupObjectCrossRef WHERE scannedObjectId=:scannedObjectId AND scannedGroupId=:scannedGroupId"
    )
    suspend fun deleteObjectFromScannedGroup(
        scannedGroupId: Long,
        scannedObjectId: Long
    )

    @Query(
        "DELETE FROM ScannedGroupObjectCrossRef WHERE scannedGroupId=:scannedGroupId"
    )
    suspend fun deleteScannedGroupInCrossRef(
        scannedGroupId: Long
    )

}