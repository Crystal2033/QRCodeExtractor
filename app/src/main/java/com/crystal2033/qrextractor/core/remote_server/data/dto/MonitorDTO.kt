package com.crystal2033.qrextractor.core.remote_server.data.dto

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor

data class MonitorDTO(
    override val id: Long,
    override val image: Bitmap?,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO<Monitor> {
    override fun toModel(): Monitor {
        return Monitor(
            id, image, inventoryNumber, name, cabinetId
        )
    }
}