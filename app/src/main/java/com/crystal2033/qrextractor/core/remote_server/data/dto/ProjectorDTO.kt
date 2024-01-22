package com.crystal2033.qrextractor.core.remote_server.data.dto

import androidx.compose.ui.graphics.asAndroidBitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters

data class ProjectorDTO(
    override val id: Long,
    override val image: String,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<Projector> {
    override fun toModel(): Projector {
        return Projector(
            id,
            StaticConverters.fromBytesIntoImageBitmap(image).asAndroidBitmap(),
            inventoryNumber,
            name,
            cabinetId
        )
    }
}