package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Chair(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedModel<ChairDTO>, QRScannableData {
    override fun toDTO(): ChairDTO {
        return ChairDTO(
            id, image, inventoryNumber, name, cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.CHAIR
    }

    override fun getDatabaseID(): Long {
        return id
    }
}