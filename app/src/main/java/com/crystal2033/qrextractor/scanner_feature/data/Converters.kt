package com.crystal2033.qrextractor.scanner_feature.data

import com.crystal2033.qrextractor.core.scan_model.ScannedObject
import com.crystal2033.qrextractor.scanner_feature.data.util.JsonParser
import com.google.gson.reflect.TypeToken


class Converters(
    private val jsonParser: JsonParser
) {
    fun fromScannedObjectsJson(json: String): ScannedObject?{
        return jsonParser.fromJson<ScannedObject>(
            json = json,
            type = object : TypeToken<ScannedObject>(){}.type
        )
    }

    fun toScannedObjectsJson(scannedObject: ScannedObject): String{
        return jsonParser.toJson(
            obj = scannedObject,
            type = object : TypeToken<ScannedObject>(){}.type
        )?: "[]"
    }
}
//package com.crystal2033.qrextractor.scanner_feature.data
//
//import androidx.room.ProvidedTypeConverter
//import androidx.room.TypeConverter
//import com.crystal2033.cacheddictionary.feature_dictionary.data.util.JsonParser
//import com.crystal2033.cacheddictionary.feature_dictionary.domain.model.Meaning
//import com.google.gson.reflect.TypeToken
//
//@ProvidedTypeConverter
//class com.crystal2033.qrextractor.scanner_feature.data.Converters(
//    private val jsonParser: JsonParser
//) {
//    @TypeConverter
//    fun fromMeaningsJson(json: String): List<Meaning> {
//        return jsonParser.fromJson<ArrayList<Meaning>>(
//            json,
//            object : TypeToken<ArrayList<Meaning>>() {}.type
//        ) ?: emptyList()
//    }
//
//    @TypeConverter
//    fun toMeaningsJson(meanings: List<Meaning>): String {
//        return jsonParser.toJson(
//            meanings,
//            object : TypeToken<ArrayList<Meaning>>() {}.type
//        ) ?: "[]"
//    }
//}

