package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class SystemUnit(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedModel<SystemUnitDTO>, QRScannableData {
    override fun toDTO(): SystemUnitDTO {
        return SystemUnitDTO(
            id, image, inventoryNumber, name, cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.SYSTEM_UNIT
    }

    override fun getDatabaseID(): Long {
        return id
    }
}