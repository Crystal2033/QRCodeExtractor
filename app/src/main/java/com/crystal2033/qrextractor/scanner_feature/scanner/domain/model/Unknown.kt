package com.crystal2033.qrextractor.scanner_feature.scanner.domain.model

import com.crystal2033.qrextractor.core.model.DatabaseObjectTypes

class Unknown(val message: String) : QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.UNKNOWN
    }

    override fun getDatabaseID(): Long {
        return 0
    }
}