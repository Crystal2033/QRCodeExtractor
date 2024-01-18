package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector

data class ProjectorDTO(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<Projector> {
    override fun toModel(): Projector {
        return Projector(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}