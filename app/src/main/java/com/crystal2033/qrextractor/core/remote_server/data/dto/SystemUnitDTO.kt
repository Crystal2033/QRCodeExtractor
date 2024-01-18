package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit

data class SystemUnitDTO(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<SystemUnit> {
    override fun toModel(): SystemUnit {
        return SystemUnit(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}
