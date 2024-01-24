package com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle

import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.remote_server.data.model.Branch
import com.crystal2033.qrextractor.core.remote_server.data.model.Building
import com.crystal2033.qrextractor.core.remote_server.data.model.Cabinet
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization

data class UserAndPlaceBundle(
    val user: User,
    val branch: Branch,
    val building: Building,
    val cabinet: Cabinet,
    val organization: Organization
) {
}