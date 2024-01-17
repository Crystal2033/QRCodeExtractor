package com.crystal2033.qrextractor.core.remote_server.data.dto

import com.crystal2033.qrextractor.core.remote_server.data.model.Building

data class BuildingDTO(
    val id: Long,
    val address: String,
    val branchId: Long
) {
    private fun toBuilding() : Building{
        return Building(
            id, address, branchId
        )
    }
}