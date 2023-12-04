package com.crystal2033.qrextractor.add_object_feature.general.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes

data class QRCodeStickerInfo(
    var qrCode: ImageBitmap? = null,
    var essentialName: String = "",
    var inventoryNumber: String = "",
    var databaseObjectTypes: DatabaseObjectTypes = DatabaseObjectTypes.UNKNOWN,
    var stickerSize: StickerSize = StickerSize.NORMAL
    //val scannedTableNameAndId: ScannedTableNameAndId? = null
) {
    enum class StickerSize(val sizeDp: Dp, val sizeDpString: String, val bitmapSize: Int) {
        SMALL(50.dp, "100x100", 100),
        NORMAL(100.dp, "200x200", 200),
        BIG(150.dp, "300x300", 300)
    }
}
