package com.crystal2033.qrextractor.scanner_feature.scanner.data.util

import java.lang.reflect.Type

interface JsonParser {
    fun <T> fromJson(json: String, type: Type): T?

    fun <T> toJson(obj: T, type: Type): String?
}