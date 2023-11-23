package com.crystal2033.qrextractor.add_object_feature.objects_menu.domain.model

import android.graphics.Bitmap

data class QRCodeStickerInfo(
    val qrCode: Bitmap,
    val essentialName: String,
    val inventoryNumber: String
)
