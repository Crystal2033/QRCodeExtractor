package com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.DeviceInfoInQRCodeRepresenter
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndID

@Entity(tableName = "ScannedObject")
data class ScannedObjectEntity(
    val tableNameInRemoteDb: String,
    val tableIdInRemoteDb: Long,

    @PrimaryKey(autoGenerate = true)
    var scannedObjectId: Long= 0
){
    fun toScannedTableNameAndId() : ScannedTableNameAndID {
        return ScannedTableNameAndID(tableNameInRemoteDb, tableIdInRemoteDb)
    }
}
