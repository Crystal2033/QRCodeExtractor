package com.crystal2033.qrextractor.core.dto

import com.crystal2033.qrextractor.core.model.WorkSpace

data class WorkSpaceDto(
    val id: Long
) {
    fun toWorkSpace(): WorkSpace {
        return WorkSpace(
            id = id
        )
    }
}