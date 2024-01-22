package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData

data class Desk(
    override var id: Long,
    override var image: Bitmap?,
    override var inventoryNumber: String,
    override var name: String,
    override var cabinetId: Long
) : InventarizedModel<DeskDTO>, QRScannableData {
    override fun toDTO(): DeskDTO {
        return DeskDTO(
            id,
            image = image?.let { StaticConverters.fromBitmapToString(it) } ?: "",
            inventoryNumber,
            name,
            cabinetId
        )
    }

    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.DESK
    }

    override fun getDatabaseID(): Long {
        return id
    }
}
