package com.crystal2033.qrextractor.core.remote_server.data.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO

data class Chair(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedModel<ChairDTO> {
    override fun toDTO(): ChairDTO {
        return ChairDTO(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}