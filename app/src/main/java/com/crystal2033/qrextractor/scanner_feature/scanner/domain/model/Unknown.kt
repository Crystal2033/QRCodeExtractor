package com.crystal2033.qrextractor.scanner_feature.scanner.domain.model

import android.graphics.Bitmap
import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

class Unknown(val message: String,
              override var id: Long = 0L,
              override var image: Bitmap? = null,
              override var inventoryNumber: String = "",
              override var name: String = "",
              override var cabinetId: Long = 0L
) : InventarizedAndQRScannableModel {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.UNKNOWN
    }

    override fun getDatabaseID(): Long {
        return 0
    }
}