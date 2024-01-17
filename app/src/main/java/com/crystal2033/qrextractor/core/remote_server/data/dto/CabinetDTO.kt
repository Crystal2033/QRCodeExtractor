package com.crystal2033.qrextractor.core.remote_server.data.dto

import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet

data class CabinetDTO(
    val id: Long,
    val name: String,
    val buildingId: Long
) {
    private fun toCabinet() : Cabinet {
        return Cabinet(
            id, name, buildingId
        )
    }
}