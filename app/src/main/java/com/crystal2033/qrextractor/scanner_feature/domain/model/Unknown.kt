package com.crystal2033.qrextractor.scanner_feature.domain.model

class Unknown : QRScannableData {
    override fun getDatabaseTableName(): DatabaseObjectTypes {
        return DatabaseObjectTypes.UNKNOWN
    }

    override fun getDatabaseID(): Int {
        return -1;
    }
}