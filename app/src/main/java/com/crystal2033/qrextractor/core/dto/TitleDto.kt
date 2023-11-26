package com.crystal2033.qrextractor.core.dto

import com.crystal2033.qrextractor.core.model.Title

data class TitleDto(
    val id: Long,
    val name: String
) {
    fun toTitle(): Title {
        return Title(id = id, name = name)
    }
}