package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk

data class DeskDTO(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<Desk> {
    override fun toModel(): Desk {
        return Desk(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}
