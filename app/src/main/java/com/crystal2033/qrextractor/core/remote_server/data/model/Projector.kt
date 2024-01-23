package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters

data class Projector(
    override var id: Long = 0L,
    override var image: Bitmap? = null,
    override var inventoryNumber: String = "",
    override var name: String = "",
    override var cabinetId: Long = 0L
) : InventarizedAndQRScannableModel, ToDTOMapper<ProjectorDTO> {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.PROJECTOR
    }

    override fun getDatabaseID(): Long {
        return id
    }

    override fun toDTO(): ProjectorDTO {
        return ProjectorDTO(
            id,
            image?.let { StaticConverters.fromBitmapToString(it) } ?: "",
            inventoryNumber,
            name,
            cabinetId
        )
    }
}