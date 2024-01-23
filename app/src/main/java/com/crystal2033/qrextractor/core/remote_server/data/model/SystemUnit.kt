package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class SystemUnit(
    override var id: Long,
    override var image: Bitmap?,
    override var inventoryNumber: String,
    override var name: String,
    override var cabinetId: Long
) : InventarizedModel, ToDTOMapper<SystemUnitDTO>, QRScannableData {
    override fun toDTO(): SystemUnitDTO {
        return SystemUnitDTO(
            id,
            image?.let { StaticConverters.fromBitmapToString(it) } ?: "",
            inventoryNumber,
            name,
            cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.SYSTEM_UNIT
    }

    override fun getDatabaseID(): Long {
        return id
    }
}