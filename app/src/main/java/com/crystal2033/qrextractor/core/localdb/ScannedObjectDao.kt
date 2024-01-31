package com.crystal2033.qrextractor.core.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedObjectEntity

@Dao
interface ScannedObjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScannedObject(scannedObject: ScannedObjectEntity): Long

    @Query("DELETE FROM ScannedObject WHERE scannedObjectId=:scannedObjectID")
    suspend fun deleteScannedObjectFromDB(scannedObjectID: Long)
}