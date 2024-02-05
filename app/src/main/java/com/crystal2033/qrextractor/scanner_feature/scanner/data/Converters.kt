package com.crystal2033.qrextractor.scanner_feature.scanner.data

import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.JsonParser
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.DeviceInfoInQRCodeRepresenter
import com.google.gson.reflect.TypeToken


class Converters(
    private val jsonParser: JsonParser
) {
    fun fromJsonToScannedTableNameAndId(json: String): DeviceInfoInQRCodeRepresenter? {
        return jsonParser.fromJson<DeviceInfoInQRCodeRepresenter>(
            json = json,
            type = object : TypeToken<DeviceInfoInQRCodeRepresenter>() {}.type
        )
    }

    fun toJsonFromScannedTableAndId(scannedObject: DeviceInfoInQRCodeRepresenter): String {
        return jsonParser.toJson(
            obj = scannedObject,
            type = object : TypeToken<DeviceInfoInQRCodeRepresenter>() {}.type
        ) ?: "[]"
    }

    fun toJsonFromQRScannableData(qrScannableData: InventarizedAndQRScannableModel, orgId: Long): String {
        val deviceInfoInQRCode = DeviceInfoInQRCodeRepresenter(
            qrScannableData.getDatabaseTableName().toString().lowercase(),
            qrScannableData.getDatabaseID(),
            orgId,
            qrScannableData.inventoryNumber
        )
        return toJsonFromScannedTableAndId(deviceInfoInQRCode)
    }
}

