package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair

data class ChairDTO(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<Chair> {
    override fun toModel(): Chair {
        return Chair(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}