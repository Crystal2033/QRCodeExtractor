package com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.dto

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