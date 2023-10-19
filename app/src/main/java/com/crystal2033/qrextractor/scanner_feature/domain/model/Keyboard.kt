package com.crystal2033.qrextractor.scanner_feature.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import com.crystal2033.qrextractor.scanner_feature.data.remote.dto.WorkSpaceDto
import java.time.LocalDate

data class Keyboard(
    val id: Int,
    val image: ImageBitmap,
    val model: String,
    val startUseDate: LocalDate,
    val workSpace: WorkSpace
) : QRScannableData{
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.KEYBOARD
    }

    override fun getDatabaseID(): Int {
        return id;
    }
}
