package com.crystal2033.qrextractor.core.remote_server.data.dto

import com.crystal2033.qrextractor.core.remote_server.data.model.Branch

data class BranchDTO(
    val id: Long,
    val name: String,
    val cityName: String,
    val organizationId: Long,
    val cityId: Long
) {
    private fun toBranch(): Branch {
        return Branch(
            id, name, cityName, organizationId, cityId
        )
    }
}