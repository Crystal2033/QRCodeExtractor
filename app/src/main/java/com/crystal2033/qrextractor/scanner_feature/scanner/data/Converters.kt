package com.crystal2033.qrextractor.scanner_feature.scanner.data

import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.ScannedTableNameAndId
import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.JsonParser
import com.google.gson.reflect.TypeToken


class Converters(
    private val jsonParser: JsonParser
) {
    fun fromJsonToScannedTableNameAndId(json: String): ScannedTableNameAndId?{
        return jsonParser.fromJson<ScannedTableNameAndId>(
            json = json,
            type = object : TypeToken<ScannedTableNameAndId>(){}.type
        )
    }

    fun toJsonFromScannedTableAndId(scannedObject: ScannedTableNameAndId): String{
        return jsonParser.toJson(
            obj = scannedObject,
            type = object : TypeToken<ScannedTableNameAndId>(){}.type
        )?: "[]"
    }
}

