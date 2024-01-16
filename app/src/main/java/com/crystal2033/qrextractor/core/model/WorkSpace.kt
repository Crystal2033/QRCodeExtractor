package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.dto.WorkSpaceDto

data class WorkSpace(
    var id: Long
) {
    fun toWorkspaceDto(): WorkSpaceDto {
        return WorkSpaceDto(
            id = id
        )
    }
}