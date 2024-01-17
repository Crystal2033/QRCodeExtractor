package com.crystal2033.qrextractor.core.remote_server.data.dto

import com.crystal2033.qrextractor.core.remote_server.data.model.Organization

data class OrganizationDTO(
    val id: Long,
    val name: String
) {
    public fun toOrganization(): Organization {
        return Organization(
            id = id,
            name = name
        )
    }
}