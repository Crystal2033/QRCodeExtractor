package com.crystal2033.qrextractor.scanner_feature.scanner.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import java.time.LocalDate

data class Keyboard(
    val id: Long,
    val image: ImageBitmap,
    val model: String,
    val startUseDate: LocalDate,
    val workSpace: WorkSpace
) : QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.KEYBOARD
    }

    override fun getDatabaseID(): Long {
        return id
    }
}
