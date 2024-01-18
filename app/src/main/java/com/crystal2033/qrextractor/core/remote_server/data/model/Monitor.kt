package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Monitor(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedModel<MonitorDTO>, QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.MONITOR
    }

    override fun getDatabaseID(): Long {
        return id
    }

    override fun toDTO(): MonitorDTO {
        return MonitorDTO(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}