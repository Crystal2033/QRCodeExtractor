package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Projector(
    override var id: Long,
    override var image: Bitmap?,
    override var inventoryNumber: String,
    override var name: String,
    override var cabinetId: Long
) : InventarizedModel, ToDTOMapper<ProjectorDTO>, QRScannableData {
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