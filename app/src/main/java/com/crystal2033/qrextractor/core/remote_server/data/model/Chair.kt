package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters

data class Chair(
    override var id: Long = 0L,
    override var image: Bitmap? = null,
    override var inventoryNumber: String = "",
    override var name: String = "",
    override var cabinetId: Long = 0L
) : InventarizedAndQRScannableModel, ToDTOMapper<ChairDTO> {
    override fun toDTO(): ChairDTO {
        return ChairDTO(
            id,
            image = image?.let { StaticConverters.fromBitmapToString(it) } ?: "",
            inventoryNumber,
            name,
            cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.CHAIR
    }

    override fun getDatabaseID(): Long {
        return id
    }
}