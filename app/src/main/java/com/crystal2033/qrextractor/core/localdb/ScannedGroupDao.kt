package com.crystal2033.qrextractor.core.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupObjectCrossRef

@Dao
interface ScannedGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScannedGroup(scannedGroup: ScannedGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScannedGroupCrossRef(scannedGroupObjectCrossRef: ScannedGroupObjectCrossRef): Long

    @Delete
    suspend fun deleteScannedGroup(scannedGroup: ScannedGroupEntity)

    @Query(
        "DELETE FROM ScannedGroupObjectCrossRef WHERE scannedObjectId=:scannedObjectId AND scannedGroupId=:scannedGroupId"
    )
    suspend fun deleteObjectFromScannedGroup(
        scannedGroupId: Long,
        scannedObjectId: Long
    )

}