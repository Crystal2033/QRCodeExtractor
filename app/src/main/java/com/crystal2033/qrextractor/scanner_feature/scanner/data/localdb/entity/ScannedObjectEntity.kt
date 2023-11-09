package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.ScannedTableNameAndId

@Entity(tableName = "ScannedObject")
data class ScannedObjectEntity(
    val tableNameInRemoteDb: String,
    val tableIdInRemoteDb: Long,

    @PrimaryKey(autoGenerate = true)
    var scannedObjectId: Long= 0
){
    fun toScannedTableNameAndId() : ScannedTableNameAndId{
        return ScannedTableNameAndId(tableNameInRemoteDb, tableIdInRemoteDb)
    }
}
