package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Monitor(
    override var id: Long,
    override var image: Bitmap?,
    override var inventoryNumber: String,
    override var name: String,
    override var cabinetId: Long
) : InventarizedModel, ToDTOMapper<MonitorDTO>, QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.MONITOR
    }

    override fun getDatabaseID(): Long {
        return id
    }

    override fun toDTO(): MonitorDTO {
        return MonitorDTO(
            id, image?.let { StaticConverters.fromBitmapToString(it) } ?: "", inventoryNumber, name, cabinetId
        )
    }
}