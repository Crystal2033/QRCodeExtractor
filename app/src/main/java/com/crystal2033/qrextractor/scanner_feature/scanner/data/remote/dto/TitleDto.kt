package com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.dto

import com.crystal2033.qrextractor.core.model.Title

data class TitleDto(
    val id: Long,
    val name: String
) {
    fun toTitle(): Title {
        return Title(id = id, name = name)
    }
}