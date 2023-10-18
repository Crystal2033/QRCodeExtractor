package com.crystal2033.qrextractor.scanner_feature.domain.model

interface QRScannableData {
    fun getDatabaseTableName():DatabaseObjectTypes
    fun getDatabaseID(): Int
}