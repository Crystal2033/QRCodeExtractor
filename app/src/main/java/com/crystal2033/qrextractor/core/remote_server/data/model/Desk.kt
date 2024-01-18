package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Desk(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedModel<DeskDTO>, QRScannableData {
    override fun toDTO(): DeskDTO {
        return DeskDTO(
            id, image, inventoryNumber, name, cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.DESK
    }

    override fun getDatabaseID(): Long {
        return id
    }
}
