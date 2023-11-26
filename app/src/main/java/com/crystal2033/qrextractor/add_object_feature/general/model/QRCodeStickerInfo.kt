package com.crystal2033.qrextractor.add_object_feature.general.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId

data class QRCodeStickerInfo(
    val qrCode: Bitmap? = null,
    val essentialName: String = "",
    val inventoryNumber: String = "",
    //val scannedTableNameAndId: ScannedTableNameAndId? = null
)
