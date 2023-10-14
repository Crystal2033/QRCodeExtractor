package com.crystal2033.qrextractor.scanner_feature.data.remote.dto

import com.crystal2033.qrextractor.scanner_feature.domain.model.WorkSpace

data class WorkSpaceDto(
    val id: Int
) {
    fun toWorkSpace(): WorkSpace {
        return WorkSpace(
            id = id
        )
    }
}