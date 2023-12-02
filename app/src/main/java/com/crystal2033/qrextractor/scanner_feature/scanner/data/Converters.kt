package com.crystal2033.qrextractor.scanner_feature.scanner.data

import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.JsonParser
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId
import com.google.gson.reflect.TypeToken


class Converters(
    private val jsonParser: JsonParser
) {
    fun fromJsonToScannedTableNameAndId(json: String): ScannedTableNameAndId? {
        return jsonParser.fromJson<ScannedTableNameAndId>(
            json = json,
            type = object : TypeToken<ScannedTableNameAndId>() {}.type
        )
    }

    fun toJsonFromScannedTableAndId(scannedObject: ScannedTableNameAndId): String {
        return jsonParser.toJson(
            obj = scannedObject,
            type = object : TypeToken<ScannedTableNameAndId>() {}.type
        ) ?: "[]"
    }

    fun toJsonFromQRScannableData(qrScannableData: QRScannableData): String {
        val scannedTableNameAndId = ScannedTableNameAndId(
            qrScannableData.getDatabaseTableName().toString().lowercase(),
            qrScannableData.getDatabaseID()
        )
        return toJsonFromScannedTableAndId(scannedTableNameAndId)
    }
}

