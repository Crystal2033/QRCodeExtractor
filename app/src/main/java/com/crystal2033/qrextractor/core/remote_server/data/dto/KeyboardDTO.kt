package com.crystal2033.qrextractor.core.remote_server.data.dto

import androidx.compose.ui.graphics.asAndroidBitmap
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.scanner.StaticConverters

data class KeyboardDTO(
    override val id: Long,
    override val image: String,
    override val inventoryNumber: String,
    override val name: String,
    override val cabinetId: Long
) : InventarizedDTO, ToModelMapper<Keyboard> {
    override fun toModel(): Keyboard {
        return Keyboard(
            id,
            image = StaticConverters.fromBytesIntoImageBitmap(image).asAndroidBitmap(),
            inventoryNumber,
            name,
            cabinetId
        )
    }
}