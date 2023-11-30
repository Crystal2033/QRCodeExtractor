package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.dto.TitleDto

data class Title(
    val id: Long,
    val name: String
) {
    fun toTitleDto(): TitleDto {
        return TitleDto(
            id = id,
            name = name
        )
    }
}