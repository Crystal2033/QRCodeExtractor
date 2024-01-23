package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters

data class SystemUnit(
    override var id: Long = 0L,
    override var image: Bitmap? = null,
    override var inventoryNumber: String = "",
    override var name: String = "",
    override var cabinetId: Long = 0L
) : InventarizedAndQRScannableModel, ToDTOMapper<SystemUnitDTO> {
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